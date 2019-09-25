package com.zst.provider.hystrix;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.hystrix.HystrixProperties;

/**
 * @description: 信号量 Hystrix
 * @author: Zhoust
 * @date: 2019/09/08 16:58
 * @version: V1.0
 */
@Slf4j
public class SemaphoreHystrixCommand extends HystrixCommand<String> {

    private Long cityId;

    public SemaphoreHystrixCommand(Long cityId) {
        //设置信号量隔离策略
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SemaphoreHystrixCommandGroup"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                                                                                   .withExecutionIsolationSemaphoreMaxConcurrentRequests(2)
                    )
        );
        this.cityId = cityId;
    }

    @Override
    protected String run() throws Exception {
        log.info("SemaphoreHystrixCommand run() 方法执行 cityId = {}", cityId);
//        if (System.currentTimeMillis() % 2 == 0) {
//            //模拟 run() 方法抛异常
//            throw new RuntimeException("123123");
//        }
        //需要信号量隔离的代码
        return LocalCache.getCityName(cityId);
    }


    /**
     * 当 run() 方法抛异常、超时、thread pool 或信号量拒绝、circuit-breaker short-circuiting 都会触发 fallback 方法
     */
    @Override
    protected String getFallback() {
        Throwable failedExecutionException = getFailedExecutionException();
        //异常堆栈打印不出来是因为这个 failedExecutionException 是 null
        log.error("LocalCache#getCityName() 方法触发降级", failedExecutionException);
        return "默认城市";
    }
}