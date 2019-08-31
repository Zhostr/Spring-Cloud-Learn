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
 * @description:
 * @author: Zhoust
 * @date: 2019/08/31 18:06
 * @version: V1.0
 */
@Slf4j
public class ProductInfoCommand extends HystrixCommand<GenericResponse<ProductInfo>> {

    private Long productId;

    public ProductInfoCommand(Long productId) {
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

}