package com.zuoban.toy.vpstools.entity;

import com.zuoban.toy.vpstools.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * 用户
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity
public class User extends BaseEntity {
    /**
     * 用户名
     */
    @Column(unique = true)
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 过期时间
     */
    private Date expireTime;
}
