package com.zuoban.toy.vpstools.service;

import com.zuoban.toy.vpstools.BaseTest;
import com.zuoban.toy.vpstools.entity.File;
import com.zuoban.toy.vpstools.entity.UserFile;
import com.zuoban.toy.vpstools.supports.holder.UserHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */
public class UserFileServiceTest extends BaseTest {
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private FileService fileService;

    @Test
    public void add() {
        for (int i = 1; i <= 10; i++) {
            File file = new File().setName("zuoban-" + i).setExtension("mp4").setMd5(UUID.randomUUID().toString()).setPath("/test").setSize(i * 1024 * 1024L).setFileSize(i + "m");
            fileService.save(file);

            UserFile userFile = new UserFile();
            userFile.setFilename(file.getName());
            userFile.setUserId(UserHolder.getUserId());
            userFile.setFileId(file.getId());
            userFileService.save(userFile);
        }

    }
}
