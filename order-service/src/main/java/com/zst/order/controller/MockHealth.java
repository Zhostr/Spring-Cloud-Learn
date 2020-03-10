package com.zst.order.controller;

import com.zst.commons.util.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/11/28 上午9:37
 * @version: V1.0
 */
@Slf4j
@RestController
public class MockHealth {

    public static Boolean healthStatus = Boolean.TRUE;

    //@ExceptionHandler(Throwable.class)
    public GenericResponse errorHandler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Exception e) {
        int responseCode = httpServletResponse.getStatus();
        log.error("Unhandled Exception! url is {}, response code is {}, msg is {}", httpServletRequest.getRequestURI(), responseCode, e.getMessage(), e);
        return GenericResponse.failed("服务器内部异常！from [" + this.getClass().getCanonicalName() + "]");
    }

    @GetMapping("changeHealth/{status}")
    public String changeStatus(@PathVariable("status") boolean status) {
        healthStatus = status;
        return "当前服务是否正常：" + healthStatus;
    }

    /**
     * 测试缺少 userId 会产生的异常情况，会导致 Http 响应码 400
     * @param userId
     * @return
     */
    @GetMapping("/query")
    public String query(@RequestParam("userId") String userId) {
        return "userId = " + userId;
    }

}