package com.zuoban.toy.vpstools.controller;

import com.zuoban.toy.vpstools.entity.DownloadTask;
import com.zuoban.toy.vpstools.service.DownloadTaskService;
import com.zuoban.toy.vpstools.supports.bean.BaseQuery;
import com.zuoban.toy.vpstools.supports.bean.RestResponse;
import com.zuoban.toy.vpstools.supports.bean.RestResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 下载任务
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@RestController
@RequestMapping("downloadTask")
@Validated
public class DownloadTaskController {
    private final DownloadTaskService downloadTaskService;

    @Autowired
    public DownloadTaskController(DownloadTaskService downloadTaskService) {
        this.downloadTaskService = downloadTaskService;
    }

    @PostMapping
    public RestResponse add(@NotEmpty String url) {
        downloadTaskService.add(url);
        return RestResponseEnum.ADD_SUCCESS.getTarget();
    }

    @GetMapping
    public RestResponse list(BaseQuery query) {
        Page<DownloadTask> list = downloadTaskService.list(query);
        return RestResponse.success(list);
    }

    @DeleteMapping
    public RestResponse delete(@NotNull @RequestBody String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        downloadTaskService.delete(idList);
        return RestResponseEnum.DELETE_SUCCESS.getTarget();
    }
}
