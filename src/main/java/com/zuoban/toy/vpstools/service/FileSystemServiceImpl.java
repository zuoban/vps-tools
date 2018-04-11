package com.zuoban.toy.vpstools.service;

import com.zuoban.toy.vpstools.vo.FileInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSystemServiceImpl implements FileSystemService {
    @Override
    public List<FileInfo> listFile(Path directory) throws IOException {
        return Files.walk(directory, FileVisitOption.FOLLOW_LINKS).filter(it -> !Files.isDirectory(it, LinkOption.NOFOLLOW_LINKS)
        ).map(it -> {
            FileInfo fileInfo = new FileInfo();
            File file = it.toFile();
            try {
                fileInfo.setFileName(file.getName());
                fileInfo.setFileSize(FileUtils.byteCountToDisplaySize(Files.size(it)));
                fileInfo.setRelativePath(directory.relativize(it).toString());
                FileTime lastModifiedTime = Files.getLastModifiedTime(it, LinkOption.NOFOLLOW_LINKS);
                LocalDateTime localDateTime = LocalDateTime.ofInstant(lastModifiedTime.toInstant(), ZoneId.systemDefault());
                fileInfo.setLastModifiedTime(localDateTime);
            } catch (IOException e) {
            }
            return fileInfo;
        }).collect(Collectors.toList());
    }
}
