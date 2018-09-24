package com.zuoban.toy.vpstools.service;

import com.zuoban.toy.vpstools.BaseTest;
import com.zuoban.toy.vpstools.entity.DownloadTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */
public class DownloadTaskServiceTest extends BaseTest {
    @Autowired
    private DownloadTaskService downloadTaskService;

    @Test
    public void add() {
        downloadTaskService.add("https://vt.media.tumblr.com/tumblr_pb67k2OHrx1xxmtts.mp4");
    }
}
