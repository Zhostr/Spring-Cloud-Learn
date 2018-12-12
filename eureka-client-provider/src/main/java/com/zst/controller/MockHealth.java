package com.zst.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/11/28 上午9:37
 * @version: V1.0
 */
@RestController
public class MockHealth {

    public static Boolean healthStatus = Boolean.TRUE;

    @GetMapping("changeHealth/{status}")
    public String changeStatus(@PathVariable("status") boolean status) {
        healthStatus = status;
        return "当前服务是否正常：" + healthStatus;
    }

}