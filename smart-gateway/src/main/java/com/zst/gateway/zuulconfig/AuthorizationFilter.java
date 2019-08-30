//package com.zst.gateway.zuulconfig;
//
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.http.HttpServletRequest;
//
//import java.util.Map;
//import java.util.Set;
//
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;
//
///**
// * @description:
// * @author: Zhoust
// * @date: 2019/02/22 14:41
// * @version: V1.0
// */
//@Component
//public class AuthorizationFilter extends ZuulFilter {
//
//    @Autowired
//    private ZuulProperties zuulProperties;
//
//    @PostConstruct
//    public void init() {
//        Map<String, ZuulProperties.ZuulRoute> routes = zuulProperties.getRoutes();
//        routes.forEach((k,v)->{
//            System.out.println("key is [" +k+"], value is "+v);
//            Set<String> sensitiveHeaders = v.getSensitiveHeaders();
//            if (sensitiveHeaders == null) {
//                System.out.println("sensitiveHeaders = null");
//            }
//            else {
//                System.out.println(sensitiveHeaders.size());
//            }
//        });
//    }
//
//    private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class.getCanonicalName());
//
//    @Override
//    public String filterType() {
//        return FilterConstants.PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return PRE_DECORATION_FILTER_ORDER + 1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//
//        String requestURI = request.getRequestURI();
//        String downstreamUrl = (String)currentContext.get(REQUEST_URI_KEY);
//        String routeId = (String)currentContext.get(PROXY_KEY);
//        String origin = request.getHeader("origin");
//        logger.info("full uri is [{}], downstream Url is [{}], invoke which service [{}], origin header is {}", requestURI, downstreamUrl, routeId, origin);
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext currentContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = currentContext.getRequest();
//        String authorization = request.getHeader("Authorization");
//        if (authorization == null) {
//            currentContext.setSendZuulResponse(false);
//        }
//        return null;
//    }
//
//}