package com.zst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 *      @EnableCircuitBreaker    开启熔断器功能
 *
 * @description:
 * @author: Zhoust
 * @date: 2018/12/13 下午2:19
 * @version: V1.0
 */
@EnableCircuitBreaker
@SpringBootApplication
public class SmartGateway {

    public static void main(String[] args) {
        SpringApplication.run(SmartGateway.class, args);
    }

}