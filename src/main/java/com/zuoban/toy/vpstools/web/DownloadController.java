package com.zuoban.toy.vpstools.web;

import com.zuoban.toy.vpstools.consts.URLConsts;
import com.zuoban.toy.vpstools.properties.StorageProperties;
import com.zuoban.toy.vpstools.service.DownloadService;
import com.zuoban.toy.vpstools.service.FileSystemService;
import com.zuoban.toy.vpstools.vo.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 下载 controller
 *
 * @author wangjinqiang
 */
@Controller
public class DownloadController {
    private final DownloadService downloadService;

    private final FileSystemService fileSystemService;

    private final StorageProperties storageProperties;

    private static Path savePath;

    @Autowired
    public DownloadController(DownloadService downloadService, FileSystemService fileSystemService, StorageProperties storageProperties) {
        this.downloadService = downloadService;
        this.fileSystemService = fileSystemService;
        this.storageProperties = storageProperties;
        savePath = Paths.get(storageProperties.getLocation());
    }


    @GetMapping({"/home", "/"})
    private String downloadPage(Model model) {
        try {
            List<FileInfo> files = fileSystemService.listFile(Paths.get(storageProperties.getLocation()));
            model.addAttribute("files", files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "home";
    }


    @GetMapping(value = "download-file")
    private ResponseEntity downloadFile(String path) throws IOException {
        Assert.isTrue(!path.contains(".."), "路径有误");
        Path file = savePath.resolve(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(file.getFileName().toString().getBytes("UTF-8"), "ISO8859-1"));
        byte[] bytes = IOUtils.toByteArray(file.toUri());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);
    }

    @GetMapping(value = "delete-file")
    private String deleteFile(String path) throws IOException {
        Assert.isTrue(!path.contains(".."), "路径有误");
        Path file = savePath.resolve(path);
        Files.delete(file);
        return URLConsts.SUCCESS_PAGE;
    }

    @GetMapping("/login")
    private String login() {
        return "login";
    }

    @PostMapping("/download")
    private String download(String url, @RequestParam(value = "aria2", defaultValue = "false") Boolean aria2) {
        try {
            if (aria2) {
                downloadService.aria2Download(url);
            } else {
                downloadService.download(url);
            }
            return URLConsts.SUCCESS_PAGE;
        } catch (Exception e) {
            return URLConsts.ERROR_PAGE;
        }
    }
}
