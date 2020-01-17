package com.zst.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @description: 自定义 GatewayFilter
 * @author: Zhoust
 * @date: 2019/10/16 下午7:09
 * @version: V1.0
 */
@Slf4j
@Component
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {

    public CustomGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * 默认实现会把 GatewayFilterFactory 去掉
     * @see NameUtils#normalizeFilterFactoryName(java.lang.Class)
     * @return
     */
    @Override
    public String name() {
        return CustomGatewayFilterFactory.class.getSimpleName();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            URI uri = serverHttpRequest.getURI();
            //path、uri 受前面 Filter 的影响，就是跟 Filter 的顺序有关
            log.info("uri is : {}", uri);
            log.info("serverHttpRequest.getId() is : {}", serverHttpRequest.getId());
            RequestPath requestPath = serverHttpRequest.getPath();
            log.info("serverHttpRequest.getPath() is : {}", requestPath);
            log.info("downstream service id is {}", requestPath.subPath(1, 2).value());
            log.info("下游接口是：{}", requestPath.subPath(2).value());
            log.info("Http Method is {}", serverHttpRequest.getMethodValue());
            MultiValueMap<String, String> queryParams = serverHttpRequest.getQueryParams();
            queryParams.forEach((key, value) -> log.info("Param {} = {}", key, value));
            InetSocketAddress remoteAddress = serverHttpRequest.getRemoteAddress();
            log.info("remote address is {}, hostName is {}, hostNameString is {}, port is {}", remoteAddress.getAddress(), remoteAddress.getHostName(), remoteAddress.getHostString(), remoteAddress.getPort());

            ServerHttpRequest.Builder mutate = serverHttpRequest.mutate();
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        };
    }

    public static class Config {

    }

}