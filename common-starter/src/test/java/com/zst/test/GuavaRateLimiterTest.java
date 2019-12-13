package com.zst.test;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/10/21 下午9:00
 * @version: V1.0
 */
@Slf4j
class GuavaRateLimiterTest {

    /** 每秒 x 个令牌 **/
    private static RateLimiter rateLimiter = RateLimiter.create(0.33);
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    static {
    }

    @Test
    void testRateLimiter() {
        log.info("当前时间是 {}", Instant.now().getEpochSecond());
        for (int i = 0; i < 40; i++) {
            boolean b = rateLimiter.tryAcquire();
            if (b) {
                System.out.println(rateLimiter.getRate());
                doSomething();
            }
            else {
                log.info("扔掉");
            }
        }
    }


    void doSomething() {
        log.info("通过，在 {}s do something......", Instant.now().getEpochSecond());
    }

}