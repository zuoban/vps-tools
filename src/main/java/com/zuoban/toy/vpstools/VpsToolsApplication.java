package com.zuoban.toy.vpstools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VpsToolsApplication{
    public static void main(String[] args) {
        SpringApplication.run(VpsToolsApplication.class, args);
    }
}

