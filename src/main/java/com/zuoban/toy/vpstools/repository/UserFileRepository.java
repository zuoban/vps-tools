package com.zuoban.toy.vpstools.repository;

import com.zuoban.toy.vpstools.entity.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户文件
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
public interface UserFileRepository extends JpaRepository<UserFile, Long> {

}
