package com.zst.provider.hystrix;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.HttpUtil;
import com.zst.commons.util.JsonUtil;
import com.zst.provider.model.dto.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: 使用 HystrixCommand 获取单条商品信息，Request Cache demo
 * @author: Zhoust
 * @date: 2019/08/31 18:06
 * @version: V1.0
 */
@Slf4j
public class ProductInfoCommand extends HystrixCommand<GenericResponse<ProductInfo>> {

    private Long productId;

    private static final HystrixCommandKey COMMAND_KEY = HystrixCommandKey.Factory.asKey("CustomCommandKey");

    /**
     * 线程池参数
     * 1. coreSize：核心线程数（默认 10）
     * 2. maximumSize：最大线程数（默认 10）
     * 3. maxQueueSize：等待队列大小（默认为 -1 使用 SynchronousQueue，指定之后会使用 LinkedBlockingQueue）
     * 3. withQueueSizeRejectionThreshold：与 maxQueueSize 配合使用，队列的大小，取得是这两个参数的较小值 TODO
     * @param productId
     */
    @SuppressWarnings("all")
    public ProductInfoCommand(Long productId) {
        //设置 Command Group、Command Key、Thread Pool Key 以及线程池参数
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoCommandGroup"))
                    .andCommandKey(COMMAND_KEY)
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CustomThreadPool"))
                    //线程池参数
                    .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                                                                                .withCoreSize(8)
                                                                                .withMaxQueueSize(10)
                                                                                .withQueueSizeRejectionThreshold(12))
                    //断路器参数
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties.defaultSetter()
                                                    .withCircuitBreakerEnabled(true)//控制是否允许断路器工作，包括跟踪依赖服务调用的健康状况，以及对异常情况过多时是否允许触发断路。TODO 动态设置
                                                    .withCircuitBreakerRequestVolumeThreshold(20)//默认 10s 内，至少有 20 个请求经过断路器，才可能会触发断路
                                                    .withCircuitBreakerErrorThresholdPercentage(40)//在一定时间内异常比例超过该值时，触发断路（默认 50%）
                                                    .withCircuitBreakerSleepWindowInMilliseconds(3000)//断路器状态从 close 转化为 open 后，在 circuitBreakerSleepWindowInMilliseconds ms 内请求直接被断路，走 Fallback 降级（默认为 5s）
                                                    .withExecutionTimeoutInMilliseconds(20000)//默认 1s 超时，测试 Thread Reject 时设置长一点，com.netflix.hystrix.HystrixCommandProperties.default_executionTimeoutInMilliseconds
//                                                    .withFallbackIsolationSemaphoreMaxConcurrentRequests(30)//TODO
                    )
        );
        this.productId = productId;
    }

    @Override
    protected GenericResponse<ProductInfo> run() throws Exception {
        //断路测试
        if (productId == -1) {
            throw new RuntimeException("productId 异常");
        }

        GenericResponse<ProductInfo> result;
        Map<String, Object> params = new HashMap<>(1);
        params.put("productId", productId);
        String resultStr = HttpUtil.get("http://10.24.61.85/getProductInfo", params);
        //模拟接口超时降级（去掉 withExecutionTimeoutInMilliseconds），以及 Thread 拒绝策略
//        TimeUnit.SECONDS.sleep(3L);

        if (StringUtils.isNotEmpty(resultStr)) {
            //下游接口
            result = JsonUtil.fromJson(resultStr, new TypeReference<GenericResponse<ProductInfo>>() {});
            return result;
        }
        return GenericResponse.failed("调用接口失败！");
    }

    /**
     * Hystrix fallback 降级出现的时机：
     * 1、断路器处于 open 状态
     * 2、资源池已满（线程池+队列/信号量）
     * 3、run() 方法抛异常（TODO HttpUtil 不能把所有的异常都 catch 住了，不然 Hystrix 这一层无感知，将 Http 工具类产生的异常包装一下再 throw 出去。现在 HttpUtil 没有加 catch）
     * 4、run() 方法执行时间超出阈值，报 TimeoutException 异常 TODO
     *
     * fallback 也有允许的最大并发数 TODO
     * @return
     */
    @Override
    protected GenericResponse<ProductInfo> getFallback() {
        Throwable failedExecutionException = getFailedExecutionException();
//        log.info("failedExecutionException is null? {}", failedExecutionException == null);
        log.error("调用 http://10.24.61.85/getProductInfo 触发降级，productId = {}", productId);
        return GenericResponse.success(ProductInfo.builder().id(0L).name("默认名称").price(9999L).description("默认描述").cityName("默认城市").build());
    }


    //==================== Hystrix Request Cache 相关 ====================
    /**
     * 其他服务调用本服务的这个 Command，一次调用，会把首次请求下游的返回结果缓存在 Hystrix 请求上下文中。如果在这一次调用中多次用相同参数请求这个下游接口，只会发起一次网络请求，后面的都走 Hystrix 缓存
     * @return
     */
//    @Override
//    protected String getCacheKey() {
//        //默认情况下是没有 cacheKey 的，就是不开启缓存，父类返回的是 null
//        return "product_info_" + productId;
//    }

//    /**
//     * 刷新缓存基本不用
//     * @param productId
//     */
//    public static void refreshCache(Long productId) {
//        HystrixRequestCache.getInstance(COMMAND_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear("product_info_" + productId);
//    }

}