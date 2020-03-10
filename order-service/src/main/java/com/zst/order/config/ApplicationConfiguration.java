package com.zst.order.config;

import com.zst.order._native.hystrix.config.HystrixRequestContextFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/09/24 10:23
 * @version: V1.0
 */
@Slf4j
@Configuration
public class ApplicationConfiguration {

    /**
     * 注册 Hystrix 请求上下文过滤器
     * @see HystrixRequestContextFilter
     * @return
     */
    //@Bean
    public FilterRegistrationBean addHystrixRequestContextFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HystrixRequestContextFilter());
        registration.addUrlPatterns("/*");
        registration.setName("HystrixRequestContextFilter");
        registration.setEnabled(true);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        log.info("Already add HystrixRequestContextFilter for this application.");
        return registration;
    }


}