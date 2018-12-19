package com.zst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/08/27 下午11:31
 * @version: V1.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaCustomerApplication {


    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaCustomerApplication.class,args);
    }

}
