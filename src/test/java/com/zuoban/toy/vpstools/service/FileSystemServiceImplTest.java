package com.zuoban.toy.vpstools.service;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;

import static org.junit.Assert.*;

public class FileSystemServiceImplTest {

    @Test
    public void listFile() throws IOException {
        Path directory = Paths.get("/Users/wangjinqiang/Download");
        Files.walk(directory, FileVisitOption.FOLLOW_LINKS).forEach(it->{
            if (Files.isDirectory(it, LinkOption.NOFOLLOW_LINKS)){

            }else {
                try {
                    System.out.println(directory.relativize(it));
                    String size = FileUtils.byteCountToDisplaySize(Files.size(it));
                    Path fileName = it.getFileName();
                    System.out.println(fileName +" ==> "+ size);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}