package com.zst.order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixObservableCommand;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.HttpUtil;
import com.zst.commons.util.JsonUtil;
import com.zst.order._native.hystrix.ProductInfoCommand;
import com.zst.order._native.hystrix.ProductInfosCommand;
import com.zst.order._native.hystrix.SemaphoreHystrixCommand;
import com.zst.order.cloud.hystrix.ProductInfoHystrixClient;
import com.zst.order.model.dto.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description: 8080 端口
 * @author: Zhoust
 * @date: 2019/08/31 17:06
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    /**
     * 没有 Hystrix 之前的调用其他服务接口
     * @param productId
     * @return
     */
    @GetMapping("/no")
    public GenericResponse product(@RequestParam("productId") Long productId) {
        GenericResponse<ProductInfo> result;
        Map<String, Object> params = new HashMap<>(1);
        params.put("productId", productId);
        try {
            String resultStr = HttpUtil.get("http://10.24.61.85/getProductInfo", params);
            if (StringUtils.isNotEmpty(resultStr)) {
                result = JsonUtil.fromJson(resultStr, new TypeReference<GenericResponse<ProductInfo>>() {});
                log.info("返回值是 {}", result);
                return result;
            }
        } catch (Exception e) {
            log.error("product 接口异常！", e);
        }
        return GenericResponse.failed("调用接口失败！");
    }

    /**
     * HystrixCommand 线程池模型获取单条商品数据
     * @param productId
     * @return
     */
    @GetMapping("/singleProduct")
    public GenericResponse<ProductInfo> productHystrix(@RequestParam("productId") Long productId) {
        //通过线程池 command 获取商品信息
        ProductInfoCommand productInfoCommand = new ProductInfoCommand(productId);
        GenericResponse<ProductInfo> productInfoGenericResponse = productInfoCommand.execute();
        boolean circuitBreakerOpen = productInfoCommand.isCircuitBreakerOpen();
        HystrixCommandMetrics metrics = productInfoCommand.getMetrics();
        HystrixCommandMetrics.HealthCounts healthCounts = metrics.getHealthCounts();
        log.info("断路器是否开启 {}", circuitBreakerOpen);
//        SemaphoreHystrixCommand semaphoreHystrixCommand = new SemaphoreHystrixCommand(productInfo.getCityId());
        //获取本地内存的代码，会被 Hystrix 信号量进行资源隔离
//        productInfo.setCityName(semaphoreHystrixCommand.execute());
        //java.util.concurrent.TimeoutException: null
        // at com.netflix.hystrix.AbstractCommand.handleTimeoutViaFallback
        return productInfoGenericResponse;
    }

    /**
     * 为了查看滑动窗口 10s 内的异常占比
     */
    @Scheduled(fixedRate = 1000)
    public void scheduled() {
        HystrixCommandMetrics instance = HystrixCommandMetrics.getInstance(ProductInfoCommand.COMMAND_KEY);
        if (null == instance) {
            log.info("未创建此 command");
        }
        else {
            HystrixCommandMetrics.HealthCounts healthCounts = instance.getHealthCounts();
            log.info("totalCount = {}, errorCount = {}, errorPercentage = {}", healthCounts.getTotalRequests(), healthCounts.getErrorCount(), healthCounts.getErrorPercentage());
        }
    }

    /**
     * HystrixCommand 信号量模型
     * @param cityId
     * @return
     */
    @GetMapping("/semaphore/getCityNameByCityId")
    public GenericResponse<String> semaphoreHystrix(@RequestParam("cityId") Long cityId) {
        SemaphoreHystrixCommand getCityNameCommand = new SemaphoreHystrixCommand(cityId);
        String result = getCityNameCommand.execute();
        return GenericResponse.success(result);
    }

    /**
     * Hystrix Request Cache 检查请求是否从缓存中获取
     * @param productIds 对于重复的 id，第二次查询直接走缓存
     * @return
     */
    @GetMapping("/cache")
    public GenericResponse<List<ProductInfo>> testHystrixCache(@RequestParam("productIds") String productIds) {
        List<Long> productIdList = Arrays.stream(productIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ProductInfo> productInfoList = new ArrayList<>(productIdList.size());
        for (Long productId : productIdList) {
            ProductInfoCommand productInfoCommand = new ProductInfoCommand(productId);
            GenericResponse<ProductInfo> productInfoResponse = productInfoCommand.execute();
            //productInfoCommand.isResponseFromFallback() 等等好多方法，能检查短路器状态是否为开启等
            log.info("是否从缓存中取数据 {}", productInfoCommand.isResponseFromCache());
            productInfoList.add(productInfoResponse.getData());
        }
        return GenericResponse.success(productInfoList);
    }

    /**
     * TODO 这个是异步的，会先返回结果再执行 HystrixObservableCommand
     * @param productIds
     * @return
     */
    @GetMapping("/productList")
    public GenericResponse<List<ProductInfo>> productListHystrix(@RequestParam("productIds") String productIds) {
        List<ProductInfo> productInfoList = Lists.newArrayList();
        if (StringUtils.isEmpty(productIds)) {
            return GenericResponse.failed("入参 productIds 为空！");
        }
        else {
            List<Long> productList = Stream.of(productIds.split(",")).map(Long::valueOf).collect(Collectors.toList());
            HystrixObservableCommand<GenericResponse<ProductInfo>> hystrixObservableCommand = new ProductInfosCommand(productList, "http://localhost:8081/getProductInfo");
            Observable<GenericResponse<ProductInfo>> observe = hystrixObservableCommand.observe();
            observe.subscribe(new Observer<GenericResponse<ProductInfo>>() {

                @Override
                public void onNext(GenericResponse<ProductInfo> productInfoGenericResponse) {
                    productInfoList.add(productInfoGenericResponse.getData());
                }

                /**
                 * TODO error 没打印
                 * @param e
                 */
                @Override
                public void onError(Throwable e) {
                    log.error("调用 getProductInfo 接口异常！", e);
                }

                @Override
                public void onCompleted() {
                    log.info("获取完全部商品数据");
                }
            });
        }
        System.out.println("执行这个");
        return GenericResponse.success(productInfoList);
    }


    //------------------- Spring Cloud Hystrix -------------------

    @Autowired
    private ProductInfoHystrixClient productInfoApi;

    /**
     * @param productId
     * @return
     */
    @GetMapping("/cloudSingleProduct")
    public GenericResponse<ProductInfo> hystrixSingleProduct(@RequestParam("productId") Long productId) {
       return productInfoApi.getProductInfoById(productId);
    }
}