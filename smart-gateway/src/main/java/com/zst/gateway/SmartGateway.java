package com.zst.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/12/13 下午2:19
 * @version: V1.0
 */
@Slf4j
@SpringBootApplication
public class SmartGateway {

    public static void main(String[] args) {
        SpringApplication.run(SmartGateway.class, args);
    }

    /**
     * https://www.baeldung.com/spring-cloud-gateway
     * @see StripPrefixGatewayFilterFactory#apply(StripPrefixGatewayFilterFactory.Config) newPath 这一行
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator routes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(predicateSpec -> predicateSpec.path("/server2/**")
                                                     .filters(gatewayFilterSpec -> gatewayFilterSpec.stripPrefix(4))
                                                     .uri("http://localhost:8081")
                )
                .build();
    }


}