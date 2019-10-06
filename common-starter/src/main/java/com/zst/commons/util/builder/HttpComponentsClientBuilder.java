package com.zst.commons.util.builder;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 基于 HttpComponents 实现的辅助构造类
 * @author: Zhoust
 * @date: 2019/09/27 16:19
 * @version: V1.0
 */
public final class HttpComponentsClientBuilder {

    public static CloseableHttpClient build(HttpClientProperties httpClientProperties, List<HttpRequestInterceptor> requestInterceptors, List<HttpResponseInterceptor> responseInterceptors) {
        HttpClientBuilder builder = HttpClientBuilder.create().disableCookieManagement();

        DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(httpClientProperties.getHttpRetryTimes(), httpClientProperties.getHttpRetryOnFailure());
        builder.setRetryHandler(retryHandler);
        builder.setConnectionTimeToLive(httpClientProperties.getKeepAliveTime(), TimeUnit.MILLISECONDS);

        //添加拦截器
        if (!CollectionUtils.isEmpty(requestInterceptors)) {
            requestInterceptors.forEach(builder::addInterceptorLast);
        }
        if (!CollectionUtils.isEmpty(responseInterceptors)) {
            requestInterceptors.forEach(builder::addInterceptorLast);
        }

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(httpClientProperties.getConnectionTimeout().intValue())
                .setConnectionRequestTimeout(httpClientProperties.getConnectionRequestTimeout().intValue())
                .setSocketTimeout(httpClientProperties.getSocketTimeout().intValue())
                .setRedirectsEnabled(true)
                .build();

        builder.setDefaultRequestConfig(requestConfig);
        builder.setMaxConnTotal(httpClientProperties.getMaxConnections());
        builder.setMaxConnPerRoute(128);
        builder.evictExpiredConnections();
        //空闲 6s 移除
        builder.evictIdleConnections(6, TimeUnit.SECONDS);
        return builder.build();
    }

}