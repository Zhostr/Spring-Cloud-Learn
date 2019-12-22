package com.zst.provider.cloud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.HttpUtil;
import com.zst.commons.util.JsonUtil;
import com.zst.provider.model.dto.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/12/13 下午3:11
 * @version: V1.0
 */
@Slf4j
@Component
public class ProductInfoHystrixClient {

    @HystrixCommand(fallbackMethod = "fallback")
    public GenericResponse<ProductInfo> getProductInfoById(Long productId) {
        Map<String, Object> params = new HashMap<>(1);
        if (System.currentTimeMillis() % 2 == 0) {
            throw new RuntimeException("模拟异常！");
        }
        params.put("productId", productId);
        String resultStr;
        try {
            resultStr = HttpUtil.get("http://localhost:8080/getProductInfo", params);
        } catch (IOException e) {
            throw new UndeclaredThrowableException(e);
        }
        return JsonUtil.fromJson(resultStr, new TypeReference<GenericResponse<ProductInfo>>() {});
    }

    protected GenericResponse<ProductInfo> fallback(Long productId, Throwable throwable) {
        log.error("调用 http://localhost:8080/getProductInfo 触发降级，productId = {}", productId, throwable);
        return GenericResponse.success(ProductInfo.builder().id(0L).name("默认名称").price(9999L).description("默认描述").cityName("默认城市").build());
    }

}