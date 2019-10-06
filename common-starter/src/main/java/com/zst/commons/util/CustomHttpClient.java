package com.zst.commons.util;

import com.zst.commons.util.builder.HttpClientProperties;
import com.zst.commons.util.builder.HttpComponentsClientBuilder;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/09/29 17:45
 * @version: V1.0
 */
public class CustomHttpClient {

    private CloseableHttpClient closeableHttpClient;

    @Autowired
    private HttpClientProperties httpClientProperties;

    @Autowired(required = false)
    private List<HttpRequestInterceptor> httpRequestInterceptors;

    @Autowired(required = false)
    private List<HttpResponseInterceptor> httpResponseInterceptors;

    public void init() {
        closeableHttpClient = HttpComponentsClientBuilder.build(httpClientProperties, httpRequestInterceptors, httpResponseInterceptors);
    }

}