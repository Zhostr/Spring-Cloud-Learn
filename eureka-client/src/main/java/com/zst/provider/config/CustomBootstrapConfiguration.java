package com.zst.provider.config;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Map;

/**
 * @description: 自定义环境变量配置
 * @author: Zhoust
 * @date: 2018/12/13 下午8:34
 * @version: V1.0
 */
@Slf4j
public class CustomBootstrapConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment configurableEnvironment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        //不可以被覆盖
        propertySources.addFirst(specifiedHighestPrecedenceProperties());
        //允许被覆盖的属性
        propertySources.addLast(coverAllowedProperties());
    }

    /**
     * 指定高优先级配置属性（不允许自定义，无法覆盖）
     * @return
     */
    private PropertySource<?> specifiedHighestPrecedenceProperties() {
        Map<String, Object> specifiedPropertyMap = Maps.newHashMap();
        specifiedPropertyMap.put("spring.datasource.druid.initial-size", 5);
        specifiedPropertyMap.put("spring.datasource.druid.min-idle", 5);
        specifiedPropertyMap.put("spring.datasource.druid.max-active", 50);
        specifiedPropertyMap.put("spring.datasource.druid.filters", "config,stat,slf4j");
        return new MapPropertySource("druidDatasource", specifiedPropertyMap);
    }

    /**
     * 允许被自定义的配置项
     * @return
     */
    private PropertySource<?> coverAllowedProperties() {
        Map<String, Object> specifiedPropertyMap = Maps.newHashMap();
        specifiedPropertyMap.put("server.port", 7777);
        return new MapPropertySource("serverConfig", specifiedPropertyMap);
    }

}