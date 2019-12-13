package com.zst.provider;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/08/27 下午11:15
 * @version: V1.0
 */
@Slf4j
@EnableEurekaClient
@SpringBootApplication
@MapperScan(value = "com.zst.provider.mapper")
public class Application {

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        String property = environment.getProperty("spring.datasource.druid.username");
        log.info("'spring.datasource.druid.username' is {}", property);
        log.info("management.endpoints.web.exposure.include is {}", environment.getProperty("management.endpoints.web.exposure.include"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
