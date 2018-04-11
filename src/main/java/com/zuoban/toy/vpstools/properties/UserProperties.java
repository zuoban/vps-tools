package com.zuoban.toy.vpstools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用户配置
 * @author wangjinqiang
 */
@Data
@ConfigurationProperties(prefix = "user")
@Component
public class UserProperties {
    /**
     * 密码
     */
    private String userName;
    /**
     * 用户名
     */
    private String password;
}
