package com.zst.client.controller;

import com.zst.client.model.dto.Product;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return GenericResponse.success(Product.builder().id(productId).description("黑色苹果7").price(5300L).name("iphone 7").build());
    }

}