package com.zst.client.config.environment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @description: 显示设置的环境变量
 * @author: Zhoust
 * @date: 2019/09/23 15:41
 * @version: V1.0
 */
@Slf4j
public class ServerEnvironment implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {
        log.info("environment variables check start.");
        System.out.println("\tserver.connection-timeout = " + environment.getProperty("server.connection-timeout"));
        System.out.println("\tserver.tomcat.max-threads = " + environment.getProperty("server.tomcat.max-threads"));
        System.out.println("\tserver.tomcat.max-connections = " + environment.getProperty("server.tomcat.max-connections"));
        System.out.println("\tserver.tomcat.accept-count = " + environment.getProperty("server.tomcat.accept-count"));
        log.info("environment variables check end.");
    }

}