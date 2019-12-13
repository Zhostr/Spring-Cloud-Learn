package com.zst.provider.controller.handler;

import com.zst.commons.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

/**
 * @description: 异常处理器
 * @author: Zhoust
 * @date: 2019/10/29 下午2:09
 * @version: V1.0
 */
//@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    /**
     * 这个方法不会触发如 5xx、4xx 等 HTTP 状态码异常，因为这些异常不会产生 Exception，下面第二个方法如果 Exception 为 Null，就不会调用第一个方法
     * @see DispatcherServlet#processHandlerException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     * @see DispatcherServlet#processDispatchResult(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.web.servlet.HandlerExecutionChain, org.springframework.web.servlet.ModelAndView, java.lang.Exception)
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        logger.info("进入全局异常处理器 HandlerExceptionResolver");
        GenericResponse<Object> genericResponseDTO = new GenericResponse<>();;
        Throwable trueException = ex;
        //如果是动态代理产生的检查性异常，找到被 UndeclaredThrowableException 包裹的真正异常
        if (ex instanceof UndeclaredThrowableException) {
            trueException = ((UndeclaredThrowableException) ex).getUndeclaredThrowable();
        }
        if (trueException instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mException = (MethodArgumentNotValidException) trueException;
            BindingResult bindingResult = mException.getBindingResult();
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError objectError : errors) {
                if (objectError instanceof FieldError) {
                    FieldError fError = (FieldError) objectError;
                    genericResponseDTO.addErrorMessage(fError.getObjectName(), fError.getDefaultMessage());
                } else {
                    genericResponseDTO.addErrorMessage(objectError.getObjectName(), objectError.getDefaultMessage());
                }
            }
        }
        else if (trueException instanceof BindException) {
            List<FieldError> fieldErrors = ((BindException) trueException).getFieldErrors();
            fieldErrors.forEach(fieldError -> genericResponseDTO.addErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        else {

        }
        return null;
    }

}