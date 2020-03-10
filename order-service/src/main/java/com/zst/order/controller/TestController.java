package com.zst.order.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zst.commons.util.GenericResponse;
import com.zst.order.entity.OnlineClassEntity;
import com.zst.order.mapper.OnlineClassMapper;
import com.zst.order.model.dto.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/08/31 17:11
 * @version: V1.0
 */
@Slf4j
@RestController
public class TestController {

    /**
     * 模拟 Get 接口
     * @see com.zst.order.controller.CacheController#changeProduct(java.lang.Long)
     * @param productId 商品 id
     * @return 商品详情
     */
    @GetMapping("/getProductInfo")
    public GenericResponse<ProductInfo> getProductInfo(@RequestParam("productId") Long productId, HttpServletRequest httpServletRequest) {
        return GenericResponse.success(ProductInfo.builder().id(productId).description("黑色苹果7").price(5300L).name("iphone 7").cityId(1L).build());
    }

    /**
     * 模拟 post 请求
     * @param product
     * @return
     */
    @PostMapping("/postProductInfo")
    public GenericResponse<ProductInfo> mockPost(@RequestBody ProductInfo product) {
        product.setName("测试名称");
        product.setPrice(9999L);
        return GenericResponse.success(product);
    }

    /**
     * Gateway 转发，请求到网关的 uri 地址：/public/hw/standard/section/result/init，下游接口是 /service/hw/standard/section/result/init
     * 测试 StripPrefix 和 PrefixPath
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/service/hw/standard/section/result/init")
    public GenericResponse<String> testGatewayPathRewrite(HttpServletRequest httpServletRequest) {
        log.info("uri is [{}]", httpServletRequest.getRequestURI());
        return GenericResponse.success(httpServletRequest.getRequestURL().toString());
    }

    //---------- 异常处理 ----------
    /**
     * 测试全局异常和 Controller 内部的 ExceptionHandler
     * @see MockHealth#query(java.lang.String)
     * @param num
     * @return
     */
    @GetMapping("/divide-zero")
    public GenericResponse<Integer> divide(Integer num) {
        return GenericResponse.success(num/0);
    }

    //---------- 分页查询 ----------
    @Autowired
    private OnlineClassMapper onlineClassMapper;

    @GetMapping("/page/get")
    public GenericResponse<PageInfo<OnlineClassEntity>> getInfo() {
        PageHelper.startPage(3, 5);
        List<OnlineClassEntity> list = onlineClassMapper.getAllOnlineClass();
        return GenericResponse.success(new PageInfo<>(list));
    }

    @GetMapping("/timeout2")
    public GenericResponse<String> testZuulTimeOut2() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return GenericResponse.success("success");
    }

    @GetMapping("/timeout5")
    public GenericResponse<String> testZuulTimeOut5() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return GenericResponse.success("success");
    }

}