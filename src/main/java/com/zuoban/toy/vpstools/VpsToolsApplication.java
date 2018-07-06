package com.zuoban.toy.vpstools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VpsToolsApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(VpsToolsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(VpsToolsApplication.class);
    }
}
