package com.zst.provider.controller;

import com.zst.commons.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
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
    @GetMapping("change/product")
    public String changeProduct(@RequestParam("productId") Long productId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("productId", productId);
        String result = HttpUtil.get("http://localhost:8081/getProductInfo", params);
        log.info("返回值是 {}", result);
        return "success";
    }

}