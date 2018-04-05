package com.zuoban.toy.vpstools.service;

import com.zuoban.toy.vpstools.properties.StorageProperties;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DownloadServiceImpl implements DownloadService {

    private StorageProperties storageProperties;

    public DownloadServiceImpl(StorageProperties storageProperties) throws IOException {
        this.storageProperties = storageProperties;
        Path locationPath = Paths.get(storageProperties.getLocation());
        if (Files.notExists(locationPath)) {
            Files.createDirectories(locationPath);
        }
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
}
