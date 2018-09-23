package com.zuoban.toy.vpstools.service;

import cn.hutool.core.util.RuntimeUtil;
import com.zuoban.toy.vpstools.properties.StorageProperties;
import com.zuoban.toy.vpstools.properties.TumblrProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class DownloadServiceImpl implements DownloadService {

    private StorageProperties storageProperties;
    private final TumblrProperties tumblrProperties;


    @Autowired
    public DownloadServiceImpl(StorageProperties storageProperties, TumblrProperties tumblrProperties) throws IOException {
        this.storageProperties = storageProperties;
        Path locationPath = Paths.get(storageProperties.getLocation());
        if (Files.notExists(locationPath)) {
            Files.createDirectories(locationPath);
        }
        this.tumblrProperties = tumblrProperties;
    }

    @Override
    public void download(String url) {
        Path fileName = Paths.get(url).getFileName();
        try {
            FileUtils.copyURLToFile(new URL(url), Paths.get(storageProperties.getLocation(), fileName.toString()).toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void aria2Download(String url) {
        String result = RuntimeUtil.execForStr("aria2c", url);
        log.info("aria2 download result: " + result);
    }

    @Override
    public void wgetDownload(String url) {
        String result = RuntimeUtil.execForStr("wget", url);
        log.info("wget download result: " + result);
    }

    @Override
    public void tumblrDownload(String url) {
        String result = RuntimeUtil.execForStr(String.format("youtube-dl -u %s -p %s %s -o %s", tumblrProperties.getUsername(), tumblrProperties.getPassword(), url, storageProperties.getLocation()));
        log.info("tumblr download result: ", result);
    }
}
