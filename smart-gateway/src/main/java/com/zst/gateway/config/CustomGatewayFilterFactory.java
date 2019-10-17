package com.zst.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/10/16 下午7:09
 * @version: V1.0
 */
@Slf4j
@Component
public class CustomGatewayFilterFactory implements GatewayFilterFactory<CustomGatewayFilterFactory.Config> {

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            URI uri = serverHttpRequest.getURI();
            log.info("uri is : {}", uri);
            log.info("serverHttpRequest.getId() is : {}", serverHttpRequest.getId());
            log.info("serverHttpRequest.getPath() is : {}", serverHttpRequest.getPath());
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