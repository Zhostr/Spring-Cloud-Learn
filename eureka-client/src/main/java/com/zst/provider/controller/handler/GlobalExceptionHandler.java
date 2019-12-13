package com.zst.provider.controller.handler;

import com.zst.commons.util.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/10/29 下午2:45
 * @version: V1.0
 */
@Slf4j
//@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/ExceptionHandler.html
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public GenericResponse errorHandler(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Exception e) {
        int responseCode = httpServletResponse.getStatus();
        log.error("Unhandled Exception! url is {}, response code is {}, msg is {}", httpServletRequest.getRequestURI(), responseCode, e.getMessage(), e);
        return GenericResponse.failed("服务器内部异常！from [" + this.getClass().getCanonicalName() + "]");
    }

}