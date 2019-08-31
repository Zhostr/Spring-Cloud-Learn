package com.zst.provider.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.HttpUtil;
import com.zst.commons.util.JsonUtil;
import com.zst.provider.hystrix.ProductInfoCommand;
import com.zst.provider.model.dto.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/08/31 17:06
 * @version: V1.0
 */
@Slf4j
@RestController
public class CacheController {

    /**
     * 没有 Hystrix 之前的调用其他服务接口
     * @param productId
     * @return
     */
    @SuppressWarnings("all")
    @GetMapping("change/product")
    public GenericResponse<ProductInfo> changeProduct(@RequestParam("productId") Long productId) {
        GenericResponse<ProductInfo> result;
        Map<String, Object> params = new HashMap<>(1);
        params.put("productId", productId);
        String resultStr = HttpUtil.get("http://localhost:8081/getProductInfo", params);
        if (StringUtils.isNotEmpty(resultStr)) {
            result = JsonUtil.fromJson(resultStr, new TypeReference<GenericResponse<ProductInfo>>() {});
            log.info("返回值是 {}", result);
            return result;
        }
        return GenericResponse.failed("调用接口失败！");
    }

    /**
     * 使用 Hystrix
     * @param productId
     * @return
     */
    @GetMapping("change/hystrixProduct")
    public GenericResponse<ProductInfo> changeProductHystrix(@RequestParam("productId") Long productId) {
        ProductInfoCommand productInfoCommand = new ProductInfoCommand(productId);
        GenericResponse<ProductInfo> productInfoGenericResponse = productInfoCommand.execute();
        log.info("response is {}", productInfoGenericResponse);
        return productInfoGenericResponse;
    }

}