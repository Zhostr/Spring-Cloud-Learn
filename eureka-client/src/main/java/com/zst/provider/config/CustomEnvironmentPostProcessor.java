package com.zst.provider.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/09/26 18:00
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