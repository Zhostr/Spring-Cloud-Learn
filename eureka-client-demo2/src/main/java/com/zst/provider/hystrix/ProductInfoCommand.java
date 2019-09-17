package com.zst.provider.hystrix;

import com.fasterxml.jackson.core.type.TypeReference;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.HttpUtil;
import com.zst.commons.util.JsonUtil;
import com.zst.provider.model.dto.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 使用 HystrixCommand 获取单条数据
 * @author: Zhoust
 * @date: 2019/08/31 18:06
 * @version: V1.0
 */
@Slf4j
public class ProductInfoCommand extends HystrixCommand<GenericResponse<ProductInfo>> {

    private Long productId;

    public ProductInfoCommand(Long productId) {
        //这个 key 可以简单认为是一个连接池名称
        super(HystrixCommandGroupKey.Factory.asKey("ProductInfoCommandGroup"));
        this.productId = productId;
    }

    @Override
    @SuppressWarnings("all")
    protected GenericResponse<ProductInfo> run() throws Exception {
        GenericResponse<ProductInfo> result;
        Map<String, Object> params = new HashMap<>(1);
        params.put("productId", productId);
        String resultStr = HttpUtil.get("http://localhost:8081/getProductInfo", params);
        if (StringUtils.isNotEmpty(resultStr)) {
            result = JsonUtil.fromJson(resultStr, new TypeReference<GenericResponse<ProductInfo>>() {});
            log.info("返回值是 {}", result);
            return result;
        }
        return GenericResponse.failed("调用接口失败！");
    }

    /**
     * Hystrix 会在 run() 执行过程中出现错误、超时、线程池拒绝、断路器熔断等情况时，执行 getFallBack() 方法内的逻辑
     * 1、未触发降级原因：HttpUtil 把所有的异常都 catch 住了，Hystrix 这一层无感知
     * @return
     */
    @Override
    protected GenericResponse<ProductInfo> getFallback() {
        Throwable failedExecutionException = getFailedExecutionException();
        log.error("http://localhost:8081/getProductInfo 触发降级，productId = {}", productId, failedExecutionException);
        return GenericResponse.failed("调用接口失败！");
    }

}