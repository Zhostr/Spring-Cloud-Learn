package com.zst.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/08/27 下午11:31
 * @version: V1.0
 */
@RibbonClients(value = {@RibbonClient("provider")})
@EnableFeignClients(value = "com.zst.client.feign")
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaCustomerApplication.class,args);
    }

}
