package com.zst.order._native.hystrix;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.HttpUtil;
import com.zst.commons.util.JsonUtil;
import com.zst.order.model.dto.ProductInfo;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.HashMap;
import java.util.List;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/08/31 23:21
 * @version: V1.0
 */
public class ProductInfosCommand extends HystrixObservableCommand<GenericResponse<ProductInfo>> {

    private List<Long> productIdList;

    private String urlPrefix;

    public ProductInfosCommand(List<Long> productIdList, String urlPrefix) {
        super(HystrixCommandGroupKey.Factory.asKey("ProductInfosCommand"));
        this.productIdList = productIdList;
        this.urlPrefix = urlPrefix;
    }

    @Override
    protected Observable<GenericResponse<ProductInfo>> construct() {
        return Observable.create(new Observable.OnSubscribe<GenericResponse<ProductInfo>>() {
            @Override
            public void call(Subscriber<? super GenericResponse<ProductInfo>> subscriber) {
                for (Long productId : productIdList) {
                    try {
                        HashMap<String, Object> paramsMap = Maps.newHashMap();
                        paramsMap.put("productId", productId);
                        String resultStr = HttpUtil.get(urlPrefix, paramsMap);
                        if (StringUtils.isNotEmpty(resultStr)) {
                            GenericResponse<ProductInfo> productInfoGenericResponse = JsonUtil.fromJson(resultStr, new TypeReference<GenericResponse<ProductInfo>>() {});
                            subscriber.onNext(productInfoGenericResponse);
                        }
                        /*else {
                            subscriber.onNext(GenericResponse<ProductInfo>.failed(500, "调用接口失败！", new ProductInfo()));
                        }*/
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

}