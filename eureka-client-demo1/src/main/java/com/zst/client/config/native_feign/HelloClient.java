package com.zst.client.config.native_feign;

import feign.Feign;
import feign.Param;
import feign.RequestLine;

/**
 * @description:  Feign 使用 Demo
 * @author: Zhoust
 * @date: 2018/12/14 下午4:13
 * @version: V1.0
 */
public interface HelloClient {

    @RequestLine("GET /hello?name={name}")
    String sayHello(@Param("name") String name);

    class Main {
        public static void main(String[] args) {
            HelloClient helloClient = Feign.builder().target(HelloClient.class,"http://localhost:8848");
            System.out.println(helloClient.sayHello("dsadsa"));
        }
    }

}