package com.zst.order.controller;

import com.zst.commons.util.GenericResponse;
import com.zst.order.cloud.feign.TeacherWhiteFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/25 下午5:35
 * @version: V1.0
 */
@Slf4j
@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private TeacherWhiteFeign teacherWhiteFeign;

    @Autowired
    private Environment environment;

    @GetMapping("test")
    public GenericResponse<String> get() {
        String property = environment.getProperty("feign.hystrix.enabled");
        log.info("feign.hystrix.enabled = {}", property);
        GenericResponse<String> stringGenericResponse = teacherWhiteFeign.get();
        log.info("response = {}", stringGenericResponse == null ? "null" : stringGenericResponse.getData());
        return stringGenericResponse;
    }

}