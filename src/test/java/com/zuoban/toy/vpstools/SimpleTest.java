package com.zuoban.toy.vpstools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;

/**
 * @author wangjinqiang
 * @date 2018-09-24
 */
public class SimpleTest {
    @Test
    public void download() {
        String url = "http://download.ydstatic.com/notewebsite/downloads/YoudaoNote.dmg";
        System.out.println(FilenameUtils.getName(url));
        System.out.println(FilenameUtils.getExtension(url));

//        File hello = FileUtil.mkdir("/Users/wangjinqiang/Workspace/teamlead/vps-tools/dl");
////        System.out.println(hello.getAbsolutePath());
//        System.out.println("hello = " + hello);
//        long size = HttpUtil.downloadFile("http://download.ydstatic.com/notewebsite/downloads/YoudaoNote.dmg", hello, -1);
//        System.out.println("hello = " + hello);
//        System.out.println("size = " + size);
//        System.out.println("size = " + FileUtil.readableFileSize(size));
    }
}
