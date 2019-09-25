package com.zst.client.config.environment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @description: 自定义环境变量（TODO 为啥取不到这些值）
 * @author: Zhoust
 * @date: 2019/09/23 15:06
 * @version: V1.0
 */
@Slf4j
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("server.connection-timeout = {}s", environment.getProperty("server.connection-timeout"));
        log.info("server.max-thread = {}", environment.getProperty("server.max-thread"));
        log.info("server.max-connections = {}", environment.getProperty("server.max-connections"));
        log.info("server.accept-count = {}", environment.getProperty("server.accept-count"));
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 100;
    }
}