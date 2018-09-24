package com.zuoban.toy.vpstools.repository;

import com.zuoban.toy.vpstools.entity.DownloadTask;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 下载任务 repository
 * @author wangjinqiang
 * @date 2018-09-23
 */
public interface DownloadTaskRepository extends JpaRepository<DownloadTask, Long> {
}
