package com.zst.order._native.feign;

import com.zst.commons.util.GenericResponse;
import com.zst.order.model.dto.ProductInfo;
import feign.*;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.template.UriTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 常用注解
 * 1、@RequestLine：定义请求类型（HttpMethod）、Uri 模版（UriTemplate）以及占位符
 * 2、@Param：定义请求参数名，这个的值将会被用于解析占位符
 * 3、@Headers：Method、Type
 *
 * 可定制编码器和解码器
 *    decoder 对应的是解析响应
 *    encoder 对应的是编码请求
 *
 * @see Request.HttpMethod
 * @see UriTemplate
 * @
 * @description:  Feign 使用 Demo
 * @author: Zhoust
 * @date: 2018/12/14 下午4:13
 * @version: V1.0
 */
public interface NativeFeign {

    /**
     * 使用 Feign 发送 Get 请求，并用 GsonDecoder 解码响应
     * @param productId
     * @param auth
     * @return
     */
    @RequestLine("GET getProductInfo?productId={productId}")
    @Headers("Auth: {auth}")
    GenericResponse<ProductInfo> getProductInfo(@Param("productId") Long productId, @Param("auth") String auth);

    /**
     * 使用 Feign 发送 POST json 格式的请求
     * @param product
     * @return
     */
    @RequestLine("POST /postProductInfo")
    @Headers("Content-Type: application/json")
    GenericResponse<ProductInfo> post(ProductInfo product);

    @Slf4j
    class Main {
        public static void main(String[] args) {
            NativeFeign helloClient = Feign.builder()
                    .decoder(new GsonDecoder())
                    .encoder(new GsonEncoder())
                    .target(NativeFeign.class,"http://localhost:8080");
            GenericResponse<ProductInfo> productGenericResponse = helloClient.getProductInfo(103890123890L, "nanameme");
            log.info("获取商品信息，返回结果是：{}", productGenericResponse);
            System.out.println(helloClient.post(productGenericResponse.getData()));
        }
    }

}