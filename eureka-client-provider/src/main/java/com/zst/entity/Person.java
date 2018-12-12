package com.zst.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Zhoust
 * @date: 2018/08/27 下午11:18
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Integer id;

    private String name;

    private Integer age;

    private String msg;

}
