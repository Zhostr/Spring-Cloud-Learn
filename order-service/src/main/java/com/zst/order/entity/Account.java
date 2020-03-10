package com.zst.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/11 下午4:21
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Account {

    private Integer id;

    private String username;

    private Double account;

    private Date createTime;

}