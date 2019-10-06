package com.hystrix;

import com.zst.commons.util.HttpUtil;
import com.zst.provider.Application;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/09/24 15:56
 * @version: V1.0
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class HystrixTest {

    private static String baseUrl = "http://localhost:8080/hystrix/singleProduct?productId=";

    @Test
    @DisplayName("断路器测试")
    void testCircuit() {
        for (int i = 0; i < 15; i++) {
            //正常请求
            request(false);
        }

        sleep(6);

        //10s 的时间窗口未到，即使下面的这些异常请求占比已经超过 ERROR 阈值 40% 了，断路器仍旧处于 close 状态
        for (int i = 0; i < 20; i++) {
            //异常请求
            request(true);
        }

        sleep(4);

        //断路器状态从 close 转化为 open 后，在 circuitBreakerSleepWindowInMilliseconds ms 内请求直接被断路，所有请求无论正常/异常，统统直接 Fallback 降级（本例中设置的是 3s）
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                request(false);
            }
            else {
                request(true);
            }
        }

        sleep(3);

        //3s 后断路器进入 half-open 状态，Hystrix 尝试放一个请求去执行 run() 方法
        for (int i = 0; i < 10; i++) {
            //正常请求，断路器从 open 转化为 close
            request(true);
            //异常请求，断路器仍旧保持 open 状态
        }
    }

    /**
     * Hystrix 通过判断线程池或者信号量是否已满，超出容量的请求，直接 Reject 走降级，从而达到限流的作用。
     */
    @Test
    @DisplayName("线程池隔离与限流")
    void testThreadReject() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    HttpUtil.get(baseUrl + "1");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        sleep(50);
    }

    void request(boolean isError) {
        if (!isError) {
            //正常请求
            try {
                HttpUtil.get(baseUrl + "1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                HttpUtil.get(baseUrl + "-1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SneakyThrows
    void sleep(int second) {
        TimeUnit.SECONDS.sleep(second);
    }

}