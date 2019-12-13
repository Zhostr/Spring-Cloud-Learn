package com.zst.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/11/25 下午5:15
 * @version: V1.0
 */
@Slf4j
public class JustTest {

    @Test
    void test() {
        Map<String, Integer> map = new HashMap<>(10);
        map.put("11", 123123);
        map.put("11", 90);
        Integer integer = map.get("11");
        System.out.println(integer);
    }

    void print(Boolean b) {
        log.info("b = {}", b);
    }

    @Test
    void throwableCause() {
        NullPointerException nullPointerException = new NullPointerException();
        Throwable cause = nullPointerException.getCause();
        log.info("cause is {}", false, cause);
        System.out.println(cause);//null
        System.out.println("================xxxxxxxxxxx================");
    }

    void earnMoney() {

    }



}