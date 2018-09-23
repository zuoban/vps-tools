package com.zuoban.toy.vpstools.web;

import com.zuoban.toy.vpstools.consts.URLConsts;
import com.zuoban.toy.vpstools.properties.StorageProperties;
import com.zuoban.toy.vpstools.service.DownloadService;
import com.zuoban.toy.vpstools.service.FileSystemService;
import com.zuoban.toy.vpstools.vo.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
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
        File file = savePath.resolve(path).toFile();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(file.getName().getBytes("UTF-8"), "ISO8859-1"));
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource);
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
    private String download(String url, @RequestParam(value = "type", defaultValue = "0") Integer type) {
        try {
            if (type == 0) {
                downloadService.download(url);
            } else if (type == 1) {
                downloadService.aria2Download(url);
            } else if (type == 2) {
                downloadService.tumblrDownload(url);
            }
            return URLConsts.SUCCESS_PAGE;
        } catch (Exception e) {
            return URLConsts.ERROR_PAGE;
        }
    }
}
