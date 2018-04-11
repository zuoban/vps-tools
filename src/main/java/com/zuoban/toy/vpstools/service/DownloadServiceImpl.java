package com.zuoban.toy.vpstools.service;

import com.zuoban.toy.vpstools.properties.StorageProperties;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class DownloadServiceImpl implements DownloadService {

    private StorageProperties storageProperties;


    @Autowired
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

    @Override
    public void aria2Download(String url) {
        execute("aria2c", url);
    }

    @Override
    public void wgetDownload(String url) {
        execute("wget", url);
    }

    private void execute(String... command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(storageProperties.getLocation()));
            processBuilder.command(command);
            Process process = processBuilder.start();
            StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            assert exitCode == 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }

}
