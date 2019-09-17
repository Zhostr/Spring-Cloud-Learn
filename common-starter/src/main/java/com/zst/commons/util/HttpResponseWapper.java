package com.zst.commons.util;

import org.springframework.http.HttpStatus;

/**
 * @description: 外部调用返回体，status 描述 HTTP 响应码，reasonPhrase 是对响应码的描述
 * @author: Zhoust
 * @date: 2019/09/01 12:02
 * @version: V1.0
 */
public class HttpResponseWapper<T> {

    /** Http response status code **/
    private Integer status;

    /** @see HttpStatus **/
    private String reasonPhrase;

    /** 接口返回包装类
     *  data 是接口返回的内容，其中分为三部分，code、msg、data **/
    private T data;

}