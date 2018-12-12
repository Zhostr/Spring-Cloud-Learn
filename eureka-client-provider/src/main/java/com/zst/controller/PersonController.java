package com.zst.controller;

import com.zst.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
    public Person getPerson(@PathVariable("id") Integer id, HttpServletRequest httpServletRequest){
        log.info(httpServletRequest.getRequestURL().toString());
        return Person.builder()
                     .age(age)
                     .id(id)
                     .name(name)
                     .msg(httpServletRequest.getRequestURL().toString())
                     .build();
    }

}
