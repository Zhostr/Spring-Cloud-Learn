package com.zst.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *      @EnableCircuitBreaker    开启熔断器功能
 *
 * @description:
 * @author: Zhoust
 * @date: 2018/12/13 下午2:19
 * @version: V1.0
 */
//@EnableZuulProxy
@SpringBootApplication
public class SmartGateway {

    public static void main(String[] args) {
        SpringApplication.run(SmartGateway.class, args);
    }

//    @Bean
//    public PatternServiceRouteMapper serviceRouteMapper() {
//        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", "${version}/${name}");
//    }

}