package com.zuoban.toy.vpstools.controller;

import cn.hutool.core.lang.Dict;
import com.zuoban.toy.vpstools.entity.File;
import com.zuoban.toy.vpstools.entity.UserFile;
import com.zuoban.toy.vpstools.properties.AppProperties;
import com.zuoban.toy.vpstools.service.FileService;
import com.zuoban.toy.vpstools.service.FileStorageService;
import com.zuoban.toy.vpstools.service.UserFileService;
import com.zuoban.toy.vpstools.supports.bean.BaseQuery;
import com.zuoban.toy.vpstools.supports.bean.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */
@RestController
@RequestMapping("userFile")
@Slf4j
public class UserFileController {
    private final UserFileService userFileService;

    private final FileService fileService;

    private final AppProperties appProperties;

    @Autowired
    public UserFileController(UserFileService userFileService, FileService fileService, FileStorageService fileStorageService, AppProperties appProperties) {
        this.userFileService = userFileService;
        this.fileService = fileService;
        this.appProperties = appProperties;
    }

    @GetMapping
    public RestResponse list(BaseQuery query) {
        Page<UserFile> list = userFileService.list(query);
        return RestResponse.success(list);
    }

    @GetMapping("download")
    public RestResponse download(Long id) {
        Optional<UserFile> userFileOptional = userFileService.findById(id);
        UserFile userFile = userFileOptional.orElseThrow(() -> new RuntimeException("文件不存在"));

        Long fileId = userFile.getFileId();

        Optional<File> fileOptional = fileService.findById(fileId);
        File file = fileOptional.orElseThrow(() -> new RuntimeException("文件不存在"));

        Dict result = new Dict(2).set("path", file.getPath()).set("urlPrefix", appProperties.getUrlPrefix());

        return RestResponse.success(result);

//        Resource resource = fileStorageService.loadFileAsResource(file.getPath());
//
//        // Try to determine file's content type
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            log.info("Could not determine file type.");
//        }
//
//        // Fallback to the default content type if type could not be determined
//        if (contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
    }
}
