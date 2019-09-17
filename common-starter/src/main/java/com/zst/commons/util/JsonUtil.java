package com.zst.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/05/07 14:42
 * @version: V1.0
 */
@Slf4j
public class JsonUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TO_OBJECT_ERROR = "Json to object error!";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //反序列化时忽略 json 中存在但实体类中不存在的属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //序列化 Date 类型为 String，以及反序列化 JSON String 为 Date 时，指定时区
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        OBJECT_MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        //序列化时忽略值为 null 的字段，不包括基本数据类型
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T getObjectFromJson(String json, Class<T> tClass) {
        T result = null;
        try {
            result =  OBJECT_MAPPER.readValue(json, tClass);
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
            node = OBJECT_MAPPER.readTree(str);
        } catch (IOException e) {
            log.error("jackson parse str to jsonNode error. str = {}", str, e);
            return null;
        }
        return node;
    }

    /**
     * obj 可以是 POJO、Collection 或数组，如果对象为 null，就返回 "null"；如果集合为空集合，返回 "[]"
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        String rst = null;
        try {
            rst = OBJECT_MAPPER.writeValueAsString(obj);
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
            return OBJECT_MAPPER.readValue(json, type);
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
            return (T) OBJECT_MAPPER.readValue(json, typeRef);
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
            return OBJECT_MAPPER.readValue(json, getCollectionType(HashMap.class, k, v));
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
            return OBJECT_MAPPER.readValue(json, getCollectionType(ArrayList.class, t));
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
            return OBJECT_MAPPER.convertValue(obj, getCollectionType(HashMap.class, k, v));
        } catch (Exception e) {
            log.error("object convert map error!",e);
            return null;
        }
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        String s = JsonUtil.toJson(list);
        System.out.println(s);
    }

}