package com.zst.client.config.native_feign;

import feign.Feign;
import feign.gson.GsonEncoder;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/03/05 20:55
 * @version: V1.0
 */
public class Main {

    public static void main(String[] args) {
        HelloClient target1 = Feign.builder()
                                   .encoder(new GsonEncoder())
                                   .target(HelloClient.class, "http://localhost:8848");
        System.out.println(target1.sayHello("dsada"));
    }

}