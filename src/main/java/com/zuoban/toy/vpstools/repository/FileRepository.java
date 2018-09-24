package com.zuoban.toy.vpstools.repository;

import com.zuoban.toy.vpstools.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 文件 Repository
 * @author wangjinqiang
 * @date 2018-09-23
 */
public interface FileRepository extends JpaRepository<File, Long> {
}
