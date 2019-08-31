package com.zst.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @description: 自定义返回
 * @author: 戈子根<ge_zg@suixingpay.com>
 * @date: 2017/11/21 上午11:16
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GenericResponse<T> {

    /**
     * 默认成功 code
     */
    public static final String DEFAULT_SUCCESS_CODE = "0";

    /**
     * 默认成功 message
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * 默认失败 code
     */
    public static final String DEFAULT_FAILED_CODE = "9999";

    /**
     * 默认失败 message
     */
    public static final String DEFAULT_FAILED_MESSAGE = "FAILED";

    @Builder.Default
    private String code = DEFAULT_SUCCESS_CODE;

    @Builder.Default
    private String message = DEFAULT_SUCCESS_MESSAGE;

    private T data;

    /**
     * 返回成功方法
     *
     * @return
     */
    public static GenericResponse success() {
        return success(null);
    }

    /**
     * 返回带数据成功方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> GenericResponse success(T data) {
        return success("0", "SUCCESS", data);
    }

    /**
     * 自定义 code message 成功方法
     *
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> GenericResponse success(String code, String message, T data) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(message);
        return GenericResponse.<T>builder().code(code).message(message).data(data).build();
    }

    /**
     * 默认失败方法
     *
     * @return
     */
    public static GenericResponse failed() {
        return failed(DEFAULT_FAILED_CODE, DEFAULT_FAILED_MESSAGE);
    }

    /**
     * 自定义 message 失败方法
     *
     * @param message
     * @return
     */
    public static GenericResponse failed(String message) {
        return failed(DEFAULT_FAILED_CODE, message);
    }

    /**
     * 自定义code message失败方法
     *
     * @param code
     * @param message
     * @return
     */
    public static GenericResponse failed(String code, String message) {
        return GenericResponse.builder().code(code).message(message).build();
    }

    @Override
    @SneakyThrows(JsonProcessingException.class)
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
