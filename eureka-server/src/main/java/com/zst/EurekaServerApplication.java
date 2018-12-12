package com.zst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/08/27 下午11:12
 * @version: V1.0
 */
@RestController
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    @Autowired
    private Environment environment;


    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class,args);
    }

    @GetMapping("getName")
    public String getName() {
        return String.format("姓名:%s,性别:%s",environment.getProperty("info.name"),environment.getProperty("info.gender"));
    }

}
