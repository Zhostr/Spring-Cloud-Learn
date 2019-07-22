package com.zst.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/12/26 上午8:16
 * @version: V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "info")
public class Person {

    private String name;

    private String gender;

    private Integer age;

}