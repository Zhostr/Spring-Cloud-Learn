package com.zst.provider.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.zst.provider.model.dto.ProductInfo;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/08/31 18:06
 * @version: V1.0
 */
public class ProductInfoCommand extends HystrixCommand<ProductInfo> {

    private Long productId;

    protected ProductInfoCommand(Long productId) {
        super(HystrixCommandGroupKey.Factory.asKey("ProductInfoCommandGroup"));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        return null;
    }

}