package com.zst.server.controller;

import com.zst.server.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/12/26 上午8:18
 * @version: V1.0
 */
@RestController
public class PersonController {

    @Autowired
    private Environment environment;

    @Autowired
    private Person person;

    @GetMapping("getPerson")
    public Person getPerson() {
        return person;
    }

    @GetMapping("getName")
    public String getName() {
        return String.format("姓名:%s,性别:%s",environment.getProperty("info.name"),environment.getProperty("info.gender"));
    }

}