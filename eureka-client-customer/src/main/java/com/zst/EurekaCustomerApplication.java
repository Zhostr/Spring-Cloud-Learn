package com.zst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/08/27 下午11:31
 * @version: V1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaCustomerApplication.class,args);
    }

}
