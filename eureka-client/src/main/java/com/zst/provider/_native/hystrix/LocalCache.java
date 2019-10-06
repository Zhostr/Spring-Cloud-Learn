package com.zst.provider._native.hystrix;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/09/09 20:14
 * @version: V1.0
 */
@Slf4j
public class LocalCache {

    private static Map<Long, String> CITY_MAP = Maps.newHashMap();

    static {
        CITY_MAP.put(1L, "北京");
    }

    /**
     * 通过 cityId 获取 cityName
     * @param cityId
     * @return
     */
    public static String getCityName(Long cityId) {
//        try {
//            //模拟耗时操作
//            TimeUnit.SECONDS.sleep(2L);
//        } catch (InterruptedException e) {
//            log.error("something went wrong in LocalCache#getCityName", e);
//        }
        return CITY_MAP.get(cityId);
    }

}