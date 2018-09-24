package com.zuoban.toy.vpstools.repository;

import com.zuoban.toy.vpstools.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户 repository
 * @author wangjinqiang
 * @date 2018-09-23
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
