package com.zuoban.toy.vpstools.service;

import cn.hutool.core.collection.CollUtil;
import com.zuoban.toy.vpstools.BaseTest;
import com.zuoban.toy.vpstools.entity.File;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */
public class FileServiceTest extends BaseTest {
    @Autowired
    private FileService fileService;

    @Test
    public void add() {
        List<File> fileList = CollUtil.newArrayList();
        for (int i = 1; i <= 10; i++) {
            File file = new File().setName("file-" + i).setExtension("mp4").setMd5(UUID.randomUUID().toString()).setPath("/test").setSize(i*1024*1024L).setFileSize(i + "m");
            fileList.add(file);
        }
        fileService.save(fileList);
    }
}
