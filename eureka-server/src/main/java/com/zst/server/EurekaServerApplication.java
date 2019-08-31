package com.zst.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/08/27 下午11:12
 * @version: V1.0
 */
@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    private ContextRefresher contextRefresher;

    public EurekaServerApplication(ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class,args);
    }

//    @Scheduled(fixedRate = 30000L)
//    public void refresh() {
//        Set<String> changedKeys = contextRefresher.refresh();
//        if(!changedKeys.isEmpty()) {
//            log.info("Those keys has changed! {}", changedKeys);
//        }
//    }

}
