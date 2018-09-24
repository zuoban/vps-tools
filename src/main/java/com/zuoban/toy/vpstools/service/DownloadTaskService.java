package com.zuoban.toy.vpstools.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import com.zuoban.toy.vpstools.constants.DownloadTaskConstants;
import com.zuoban.toy.vpstools.entity.DownloadTask;
import com.zuoban.toy.vpstools.entity.File;
import com.zuoban.toy.vpstools.entity.UserFile;
import com.zuoban.toy.vpstools.properties.AppProperties;
import com.zuoban.toy.vpstools.repository.DownloadTaskRepository;
import com.zuoban.toy.vpstools.service.base.BaseService;
import com.zuoban.toy.vpstools.supports.bean.BaseQuery;
import com.zuoban.toy.vpstools.supports.holder.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */
@Service
@Slf4j
public class DownloadTaskService extends BaseService<DownloadTask, DownloadTaskRepository> {

    private final AppProperties appProperties;

    private final FileService fileService;

    private final UserFileService userFileService;

    @Autowired
    public DownloadTaskService(FileService fileService, UserFileService userFileService, AppProperties appProperties) {
        this.fileService = fileService;
        this.userFileService = userFileService;
        this.appProperties = appProperties;
    }


    public void add(String url) {

        String filename = FilenameUtils.getName(url);

        List<DownloadTask> tasks = findByFilenameAndUserId(filename, UserHolder.getUserId());
        Assert.isTrue(CollUtil.isEmpty(tasks), "该地址已添加");

        Optional<File> fileOptional = fileService.findByName(filename);

        if (fileOptional.isPresent()) {
            // 保存任务
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.setUrl(url)
                    .setStatus(DownloadTaskConstants.STATUS_DOWNLOADED)
                    .setUserId(UserHolder.getUserId())
                    .setFilename(filename);
            save(downloadTask);
            // 保存文件
            UserFile userFile = new UserFile();
            userFile.setFileId(fileOptional.get().getId())
                    .setFilename(filename)
                    .setUserId(UserHolder.getUserId());
            userFileService.save(userFile);
        } else {
            // 创建任务
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.setStatus(DownloadTaskConstants.STATUS_DOWNLOADING)
                    .setUserId(UserHolder.getUserId())
                    .setFilename(filename).setUrl(url);
            save(downloadTask);
        }
    }

    public Page<DownloadTask> list(BaseQuery query) {
        DownloadTask downloadTask = new DownloadTask();
        BeanUtil.fillBeanWithMap(query.ext2Map(), downloadTask, true);
        downloadTask.setFilename(query.getKeywords());

        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("filename", ExampleMatcher.GenericPropertyMatcher::contains);

        Example<DownloadTask> example = Example.of(downloadTask, matcher);
        return repository.findAll(example, query.toPageable());
    }


    private List<DownloadTask> findWaitingTasksTop10() {
        DownloadTask task = new DownloadTask().setStatus(DownloadTaskConstants.STATUS_DOWNLOADING);
        return repository.findAll(Example.of(task), PageRequest.of(0, 10)).getContent();
    }

    /**
     * 下载定时任务任务
     */
    @Scheduled(fixedDelay = 5000L)
    public void executeTask() {

        List<DownloadTask> waitingTasksTop10 = findWaitingTasksTop10();
        waitingTasksTop10.forEach(task -> {
            String url = task.getUrl();

            log.info(">>>> 执行下载任务: {}", url);
            String filename = task.getFilename();
            String filePath = task.getUserId() + "/" + filename;
            try {
                java.io.File destFile = new java.io.File(appProperties.getStorageLocation(), filePath);
                long length = HttpUtil.downloadFile(url, destFile, 120 * 1000);
                task.setStatus(DownloadTaskConstants.STATUS_DOWNLOADED);

                // 保存 file
                File file = new File();
                String fileSize = FileUtil.readableFileSize(length);
                file.setName(task.getFilename())
                        .setSize(length)
                        .setFileSize(fileSize)
                        .setExtension(FilenameUtils.getExtension(filename))
                        .setMd5(DigestUtil.md5Hex(destFile))
                        .setPath(filePath);

                fileService.save(file);

                // 保存userFile
                UserFile userFile = new UserFile()
                        .setUserId(task.getUserId())
                        .setFileId(file.getId())
                        .setFileSize(fileSize)
                        .setSize(length)
                        .setFilename(filename);

                userFileService.save(userFile);

                log.info("<<<< {} 下载完毕! 文件大小： {}", url, file.getSize());
            } catch (Exception e) {
                task.setStatus(DownloadTaskConstants.STATUS_DOWNLOAD_ERROR);
                task.setErrorMessage(ExceptionUtil.getSimpleMessage(e));
                log.error("<<<< 下载失败! ", e);
            } finally {
                save(task);
            }
        });
    }

    public List<DownloadTask> findByFilenameAndUserId(String filename, Long userId) {
        DownloadTask task = new DownloadTask().setFilename(filename).setUserId(userId);
        return repository.findAll(Example.of(task));
    }
}
