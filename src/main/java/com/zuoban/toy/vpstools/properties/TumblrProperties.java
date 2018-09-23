package com.zuoban.toy.vpstools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * tumblr 配置
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */

@Data
@Component
@ConfigurationProperties(prefix = "tumblr")
public class TumblrProperties {

    private String username;
    private String password;
}
