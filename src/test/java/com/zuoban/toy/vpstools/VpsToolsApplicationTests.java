package com.zuoban.toy.vpstools;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class VpsToolsApplicationTests {

    @Test
    public void contextLoads() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String url = "";
        processBuilder.command("aria2c",url);
        processBuilder.directory(new File(System.getProperty("user.home")));
        Process process = processBuilder.start();
//        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
//        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        assert exitCode == 0;

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
