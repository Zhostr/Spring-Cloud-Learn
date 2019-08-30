//package com.zst.gateway.zuulconfig;
//
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
//import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
//import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
//
///**
// * @description:
// * @author: Zhoust
// * @date: 2019/05/03 11:16
// * @version: V1.0
// */
//public class DynamicRouting extends DiscoveryClientRouteLocator {
//
//    public DynamicRouting(String servletPath, DiscoveryClient discovery, ZuulProperties properties, ServiceInstance localServiceInstance) {
//        super(servletPath, discovery, properties, localServiceInstance);
//    }
//
//
//
//}