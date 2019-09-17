package com.zst.client.controller;

import com.zst.client.model.dto.Product;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/08/31 17:11
 * @version: V1.0
 */
@Slf4j
@RestController
public class ProductController {

    /**
     * 模拟接口
     * @see com.zst.provider.controller.CacheController#changeProduct(java.lang.Long)
     * @param productId
     * @return
     */
    @GetMapping("getProductInfo")
    public GenericResponse<Product> getProductInfo(@RequestParam("productId") Long productId) {
        log.info("getProductInfo 接口，入参是 productId= {}", productId);
        return GenericResponse.success(Product.builder().id(productId).description("黑色苹果7").price(5300L).name("iphone 7").cityId(1L).build());
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