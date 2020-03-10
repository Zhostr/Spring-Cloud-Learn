package com.test.hystrix;

import com.zst.commons.util.HttpUtil;
import com.zst.order.OrderApplication;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/09/24 15:56
 * @version: V1.0
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OrderApplication.class)
class HystrixTest {

    private static String baseUrl = "http://localhost:8081/hystrix/singleProduct?productId=";

    @Test
    @SneakyThrows
    @DisplayName("断路器测试")
    void whenHystrixCircuitBreakerOpen() {
        //即使异常比例已经超过 50%，且次数大于 20 ，也得等到一个 metrics.healthSnapshot.intervalInMilliseconds(500ms) 后才触发熔断
//        for (int i = 0; i < 60; i++) {
//            if (i % 2 == 0) {
//                //正常请求
//                request(true);
//            }
//            else {
//                request(false);
//            }
//        }
//        TimeUnit.MILLISECONDS.sleep(250);
//        request(true);
//        request(true);

//        TimeUnit.SECONDS.sleep(10);
//
//
//        for (int i = 0; i < 60; i++) {
//            if (i % 2 == 0) {
//                //正常请求
//                request(true);
//            }
//            else {
//                request(false);
//            }
//        }
    }


    @Test
    @SneakyThrows
    void testCircuit() {
        for (int i = 0; i < 40; i++) {
            request(true);
        }
        TimeUnit.SECONDS.sleep(6);
        for (int i = 0; i < 100; i++) {
            request(true);
        }
    }

    /**
     * com.zst.provider._native.hystrix.ProductInfoCommand#run() 在这个方法里面 sleep 几秒，模拟调用下游接口超时
     * @throws IOException
     */
    @Test
    @DisplayName("断路器超时测试")
    void testHystrixTimeOut() throws IOException {
        for (int i = 0; i < 100; i++) {
            HttpUtil.get(baseUrl + "-1");
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