package com.zst.provider.controller;

import com.zst.provider.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/08/27 下午11:20
 * @version: V1.0
 */
@Slf4j
@RestController
public class PersonController {

    @Value("${person.name}")
    private String name;


    @Value("${person.age}")
    private Integer age;


    @GetMapping("get/{id}")
    public Person getPerson(@PathVariable("id") Integer id, HttpServletRequest httpServletRequest) {
        log.info(httpServletRequest.getRequestURL().toString());
        return Person.builder()
                     .age(age)
                     .id(id)
                     .name(name)
                     .msg(httpServletRequest.getRequestURL().toString())
                     .build();
    }


    @GetMapping("hello")
    public String hello(@RequestParam("name") String name, HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String header = httpServletRequest.getHeader(headerName);
            log.info("Header name is {}, value is {}", headerName, header);
        }
        log.info("name is {}", name);
        return "Hello Feign!";
    }

    @PostMapping("showPerson")
    public String jsonParam(@RequestBody Person p) {
        log.info("param = {}", p);
        return p.toString();
    }

}
