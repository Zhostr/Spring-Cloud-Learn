package com.zst.provider.controller;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/10/22 下午2:49
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("limit")
public class RateLimiterController {

    private static RateLimiter smoothRateLimiter = RateLimiter.create(0.33);

    /**
     * 测试 RateLimiter，一分钟限制 20 次接口调用
     * @param name
     * @return
     */
    @GetMapping("guava")
    public String limit(String name) {
        boolean permit = smoothRateLimiter.tryAcquire();
        if (permit) {
            log.info("请求成功");
            return "请求成功！";
        }
        log.info("接口限流");
        return "接口限流";
    }


}