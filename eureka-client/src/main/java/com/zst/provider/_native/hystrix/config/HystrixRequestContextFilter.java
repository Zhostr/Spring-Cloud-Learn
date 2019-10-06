package com.zst.provider._native.hystrix.config;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.zst.provider.config.ApplicationConfiguration;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @see ApplicationConfiguration#addHystrixRequestContextFilter()
 * @description: Hystrix Request Cache 要用到的请求上下文过滤器
 * @author: Zhoust
 * @date: 2019/09/24 07:56
 * @version: V1.0
 */
@Slf4j
public class HystrixRequestContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Something went wrong in HystrixRequestContextFilter, errMsg is {}", e.getMessage(), e);
        } finally {
            hystrixRequestContext.shutdown();
        }
    }

}