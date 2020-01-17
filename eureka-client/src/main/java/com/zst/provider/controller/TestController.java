package com.zst.provider.controller;

import com.google.common.collect.Maps;
import com.zst.commons.util.GenericResponse;
import com.zst.commons.util.JsonUtil;
import com.zst.provider.entity.Person;
import com.zst.provider.model.dto.ProductInfo;
import com.zst.provider.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
     * @see com.zst.provider.controller.CacheController#changeProduct(java.lang.Long)
     * @param productId 商品 id
     * @return 商品详情
     */
    @GetMapping("/getProductInfo")
    public GenericResponse<ProductInfo> getProductInfo(@RequestParam("productId") Long productId, HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = httpServletRequest.getHeader(headerName);
//            log.info("header {} = {}", headerName, headerValue);
        }
//        log.info("getProductInfo 接口，入参是 productId= {}", productId);
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

    //---------- 测试 HttpUtil 工具类性能 ----------

    private static String httpBinGetUrl = "http://httpbin.org/get";

    private static String httpBinPostUrl = "http://httpbin.org/post";

    private static Map<String, Object> paramMap = Maps.newHashMap();

    private static Map<String, String> headerMap = Maps.newHashMap();

    private static Person person = Person.builder().id(1000).age(24).msg("msg").name("zst").build();

    static {
        paramMap.put("name", "zst");
        paramMap.put("age", 24);
        headerMap.put("auth", "authdjsakldjaskldjklsa");
    }

    @GetMapping("/test/onlyGet")
    public Boolean testGetUrl() {
        try {
            log.info("onlyGet");
            HttpUtils.get(httpBinGetUrl + "?name=zst&age=24");
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("something went wrong! msg = {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    @GetMapping("/test/getWithParamMap")
    public Boolean testGetParamMap() {
        try {
            log.info("getWithParamMap");
            HttpUtils.get(httpBinGetUrl, paramMap);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("something went wrong! msg = {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    @GetMapping("/test/getWithAuth")
    public Boolean testGetWithAuth() {
        try {
            log.info("getWithAuth");
            HttpUtils.getWithAuth(httpBinGetUrl + "?name=zst&age=24", "auth is auth");
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("something went wrong! msg = {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    @GetMapping("/test/postForm")
    public Boolean testPostForm() {
        try {
            log.info("postForm");
            HttpUtils.postForm(httpBinPostUrl, paramMap);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("something went wrong! msg = {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    @GetMapping("/test/postFormWithAuth")
    public Boolean postFormWithAuth() {
        try {
            log.info("postFormWithAuth");
            HttpUtils.postFormWithAuth(httpBinPostUrl, paramMap, "auth is auth");
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("something went wrong! msg = {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    @GetMapping("/test/postJson")
    public Boolean postJson() {
        try {
            log.info("postJson");
            HttpUtils.postJson(httpBinPostUrl, person);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("something went wrong! msg = {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    @GetMapping("/test/postJsonWithHeaders")
    public Boolean postJsonWithHeaders() {
        try {
            log.info("postJsonWithHeaders");
            HttpUtils.postJsonWithHeaders(httpBinPostUrl, person, headerMap);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("something went wrong! msg = {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

    @GetMapping("/test/postJsonWithAuth")
    public Boolean postJsonWithAuth() {
        try {
            log.info("postJsonWithAuth");
            HttpUtils.postJsonWithAuth(httpBinPostUrl, person, "auth is auth");
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("something went wrong! msg = {}", e.getMessage(), e);
            return Boolean.FALSE;
        }
    }

}