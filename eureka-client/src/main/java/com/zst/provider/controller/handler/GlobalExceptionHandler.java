package com.zst.provider.controller.handler;

import com.zst.commons.util.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO 404 异常如何捕获？
 * @description: 全局异常处理
 * @author: Zhoust
 * @date: 2019/05/13 10:20
 * @version: V1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public GenericResponse errorHandler(HttpServletRequest httpServletRequest, Exception e) {
        log.error("Unhandled Exception! url is {}, msg is {}", httpServletRequest.getRequestURI(), e.getMessage(), e);
        return GenericResponse.failed("服务器内部异常！");
    }

}