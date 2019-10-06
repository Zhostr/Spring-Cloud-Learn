package com.zst.provider.controller;

import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.JsonUtil;
import com.zst.provider.model.dto.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/08/31 17:11
 * @version: V1.0
 */
@Slf4j
@RestController
public class TestController {

    /**
     * 模拟 Get 接口
     * @see com.zst.provider.controller.CacheController#changeProduct(java.lang.Long)
     * @param productId
     * @return
     */
    @GetMapping("getProductInfo")
    public GenericResponse<ProductInfo> getProductInfo(@RequestParam("productId") Long productId, HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpServletRequest.getHeader(headerName);
            log.info("header {} = {}", headerName, headerValue);
        }
        log.info("getProductInfo 接口，入参是 productId= {}", productId);
        return GenericResponse.success(ProductInfo.builder().id(productId).description("黑色苹果7").price(5300L).name("iphone 7").cityId(1L).build());
    }

    /**
     * 模拟 post 请求
     * @param product
     * @return
     */
    @PostMapping("postProductInfo")
    public GenericResponse<ProductInfo> mockPost(@RequestBody ProductInfo product) {
        product.setName("测试名称");
        product.setPrice(9999L);
        return GenericResponse.success(product);
    }

    /**
     * Gateway 转发，请求到网关的 uri 地址：/public/hw/standard/section/result/init，下游接口是 /service/hw/standard/section/result/init
     * 测试 StripPrefix 和 PrefixPath
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/service/hw/standard/section/result/init")
    public GenericResponse<String> testGatewayPathRewrite(HttpServletRequest httpServletRequest) {
        log.info("uri is [{}]", httpServletRequest.getRequestURI());
        return GenericResponse.success(httpServletRequest.getRequestURL().toString());
    }

}