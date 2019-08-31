package com.zst.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/05/07 14:42
 * @version: V1.0
 */
@Slf4j
@SuppressWarnings("all")
public class JsonUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TO_OBJECT_ERROR = "Json to object error!";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        //反序列化时忽略 json 中存在但实体类中不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //序列化 Date 类型为 String，以及反序列化 JSON String 为 Date 时，指定时区
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        //序列化时忽略值为 null 的字段，不包括基本数据类型
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T getObjectFromJson(String json, Class<T> tClass) {
        T result = null;
        try {
            result =  objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            log.error(TO_OBJECT_ERROR, e);
        }
        return result;
    }

    public static JsonNode getNode(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        JsonNode node;
        try {
            node = objectMapper.readTree(str);
        } catch (IOException e) {
            log.error("jackson parse str to jsonNode error. str = {}", str, e);
            return null;
        }
        return node;
    }
    public static String toJson(Object obj){
        String rst = null;
        if(obj == null || obj instanceof String){
            return (String) obj;
        }
        try {
            rst = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("object to json error!",e);
        }
        return rst;
    }

    public static <T> T fromJson(String json, Class<T> type){
        if (StringUtils.isEmpty(json)){
            return null;
        }
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            log.error(TO_OBJECT_ERROR,e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, TypeReference<T> typeRef){
        if (StringUtils.isEmpty(json)){
            return null;
        }
        try {
            return (T) objectMapper.readValue(json, typeRef);
        } catch (Exception e) {
            log.error(TO_OBJECT_ERROR,e);
            return null;
        }
    }

    public static <K, V> HashMap<K, V> toMap(String json, Class<K> k, Class<V> v) {
        if (StringUtils.isEmpty(json)){
            return null;
        }
        try {
            return objectMapper.readValue(json, getCollectionType(HashMap.class, k, v));
        } catch (Exception e) {
            log.error(TO_OBJECT_ERROR,e);
            return null;
        }
    }

    public static <T> List<T> toList(String json, Class<T> t) {
        if (StringUtils.isEmpty(json)){
            return null;
        }
        try {
            return objectMapper.readValue(json, getCollectionType(ArrayList.class, t));
        } catch (Exception e) {
            log.error(TO_OBJECT_ERROR,e);
            return null;
        }
    }

    public static <K, V> HashMap<K, V> convertMap(Object obj, Class<K> k, Class<V> v) {
        if (obj==null){
            return null;
        }
        try {
            return objectMapper.convertValue(obj, getCollectionType(HashMap.class, k, v));
        } catch (Exception e) {
            log.error("object convert map error!",e);
            return null;
        }
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


}