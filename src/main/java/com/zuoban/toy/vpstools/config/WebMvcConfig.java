package com.zuoban.toy.vpstools.config;

import com.zuoban.toy.vpstools.supports.interceptor.UserHolderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc 配置
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final UserHolderInterceptor userHolderInterceptor;

    @Autowired
    public WebMvcConfig(UserHolderInterceptor userHolderInterceptor) {
        this.userHolderInterceptor = userHolderInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userHolderInterceptor);
    }

    //重写父类提供的跨域请求处理的接口
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        registry.addMapping("/**")
                //放行哪些原始域
                .allowedOrigins("*")
                //是否发送Cookie信息
                .allowCredentials(true)
                //放行哪些原始域(请求方式)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                //放行哪些原始域(头部信息)
                .allowedHeaders("*")
                //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                .exposedHeaders("X-Token", "Header2");
    }
}
