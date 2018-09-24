package com.zuoban.toy.vpstools.service;

import com.zuoban.toy.vpstools.controller.vo.UserRegisterVO;
import com.zuoban.toy.vpstools.entity.User;
import com.zuoban.toy.vpstools.repository.UserRepository;
import com.zuoban.toy.vpstools.service.base.BaseService;
import com.zuoban.toy.vpstools.supports.util.PasswordUtil;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * 用户 service
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@Service
public class UserService extends BaseService<User, UserRepository> {
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return repository.findOne(Example.of(user));
    }

    /**
     * 注册
     *
     * @param vo 注册信息
     */
    public void register(UserRegisterVO vo) {
        String username = vo.getUsername();

        Assert.isTrue(!existsByUsername(username), "用户名已注册");

        Assert.isTrue(!existsByEmail(vo.getEmail()), "邮箱已注册");

        User user = new User()
                .setUsername(username)
                .setPassword(PasswordUtil.getPassword(username, vo.getPassword()))
                .setEmail(vo.getEmail());
        user.setCreationUser(username);
        repository.save(user);
    }

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     */
    public boolean existsByUsername(String username) {
        return repository.exists(Example.of(new User().setUsername(username)));
    }

    /**
     * 邮箱是否已存在
     *
     * @param email 邮箱
     */
    public boolean existsByEmail(String email) {
        return repository.exists(Example.of(new User().setEmail(email)));
    }
}
