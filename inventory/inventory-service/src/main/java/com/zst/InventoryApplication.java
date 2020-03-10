package com.zst;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 库存系统
 * @author: Zhoust
 * @date: 2020/03/09 下午11:15
 * @version: V1.0
 */
@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class,args);
    }

}

