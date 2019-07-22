package com.zst.client.config.native_feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Zhoust
 * @date: 2019/03/24 17:46
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
