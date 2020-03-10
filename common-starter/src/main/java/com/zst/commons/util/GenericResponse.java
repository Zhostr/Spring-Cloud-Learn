package com.zst.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 自定义接口返回体
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
    public static final Integer DEFAULT_SUCCESS_CODE = 200;

    /**
     * 默认成功 message
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "success";

    /**
     * 默认失败 code
     */
    public static final Integer DEFAULT_FAILED_CODE = 500;

    /**
     * 默认失败 message
     */
    public static final String DEFAULT_FAILED_MESSAGE = "failed";

    @Builder.Default
    private Integer code = DEFAULT_SUCCESS_CODE;

    @Builder.Default
    private String message = DEFAULT_SUCCESS_MESSAGE;

    /** 非法参数名称，如果是参数错误，会以参数名作为 key；如果是其他错误，会以 ERROR_MSG 作为 key **/
    private Map<String, String> error;

    private T data;

    /**
     * 返回带数据成功方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> GenericResponse success(T data) {
        return success(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, data);
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
    public static <T> GenericResponse success(Integer code, String message, T data) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(message);
        return GenericResponse.<T>builder().code(code).message(message).data(data).build();
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
    public static GenericResponse failed(Integer code, String message) {
        return GenericResponse.builder().code(code).message(message).build();
    }

    /**
     * 自定义code message失败方法
     *
     * @param code
     * @param message
     * @return
     */
    public static <T> GenericResponse failed(Integer code, String message, T data) {
        return GenericResponse.builder().code(code).message(message).data(data).build();
    }

    public GenericResponse<T> addErrorMessage(String paramName, String message) {
        this.code = DEFAULT_FAILED_CODE;
        if (null == paramName || null == message) {
            return this;
        }
        if (null == this.error) {
            this.error = new HashMap<>(16);
        }
        if (this.error.containsKey(paramName)) {
            String tmp = this.error.get(paramName);
            this.error.put(paramName, tmp + ";" + message);
        } else {
            this.error.put(paramName, message);
        }
        return this;
    }

    @Override
    @SneakyThrows(JsonProcessingException.class)
    public String toString() {
        return JsonUtil.OBJECT_MAPPER.writeValueAsString(this);
    }
}
