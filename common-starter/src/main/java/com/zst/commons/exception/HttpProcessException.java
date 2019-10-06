package com.zst.commons.exception;

/**
 * @description: HttpClient 调用外部请求异常体封装
 * @author: Zhoust
 * @date: 2019/09/29 18:33
 * @version: V1.0
 */
public class HttpProcessException extends RuntimeException {

    public HttpProcessException(String msg ,Exception exception) {
        super(msg, exception);
    }

}