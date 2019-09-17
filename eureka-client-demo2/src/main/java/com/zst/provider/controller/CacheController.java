package com.zst.provider.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixObservableCommand;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.HttpUtil;
import com.zst.commons.util.JsonUtil;
import com.zst.provider.hystrix.ProductInfoCommand;
import com.zst.provider.hystrix.ProductInfosCommand;
import com.zst.provider.hystrix.SemaphoreHystrixCommand;
import com.zst.provider.model.dto.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

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
    @GetMapping("/product")
    public GenericResponse<ProductInfo> product(@RequestParam("productId") Long productId) {
        GenericResponse<ProductInfo> result;
        Map<String, Object> params = new HashMap<>(1);
        params.put("productId", productId);
        try {
            String resultStr = HttpUtil.get("http://localhost:8081/getProductInfo", params);
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
     * 使用 HystrixCommand 获取单条数据
     * @param productId
     * @return
     */
    @GetMapping("/hystrixProduct")
    public GenericResponse<ProductInfo> productHystrix(@RequestParam("productId") Long productId) {
        //通过线程池 command 获取商品信息
        ProductInfoCommand productInfoCommand = new ProductInfoCommand(productId);
        GenericResponse<ProductInfo> productInfoGenericResponse = productInfoCommand.execute();
        ProductInfo productInfo = productInfoGenericResponse.getData();

        SemaphoreHystrixCommand semaphoreHystrixCommand = new SemaphoreHystrixCommand(productInfo.getCityId());
        //获取本地内存的代码，会被 Hystrix 信号量进行资源隔离
        productInfo.setCityName(semaphoreHystrixCommand.execute());
        //java.util.concurrent.TimeoutException: null
        // at com.netflix.hystrix.AbstractCommand.handleTimeoutViaFallback
        log.info("response is {}", productInfoGenericResponse);
        return productInfoGenericResponse;
    }

    /**
     * TODO 这个是异步的，会先返回结果再执行 HystrixObservableCommand
     * @param productIds
     * @return
     */
    @GetMapping("/hystrixProductList")
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

}