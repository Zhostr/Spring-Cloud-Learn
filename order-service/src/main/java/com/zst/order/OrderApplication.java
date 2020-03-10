package com.zst.order;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/08/27 下午11:15
 * @version: V1.0
 */
@Slf4j
@EnableHystrix
@EnableFeignClients
//@EnableScheduling//开启定时任务
@EnableHystrixDashboard
@EnableEurekaClient
@SpringBootApplication
@MapperScan(value = "com.zst.order.mapper")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

}
