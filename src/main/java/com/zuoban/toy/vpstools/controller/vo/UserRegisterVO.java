package com.zuoban.toy.vpstools.controller.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 用户注册vo
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@Data
@Accessors(chain = true)
public class UserRegisterVO {
    /**
     * 用户名
     */
    @NotEmpty
    private String username;
    /**
     * 密码
     */
    @NotEmpty
    private String password;
    /**
     * 邮箱
     */
    @NotEmpty
    @Email
    private String email;
}
