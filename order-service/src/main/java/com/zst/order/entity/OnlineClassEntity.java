package com.zst.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @description: online_class 表
 * @author: Zhoust
 * @date: 2020/02/03 下午4:03
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OnlineClassEntity {

    private Long id;

    private Long onlineClassId;

    private Long studentId;

    private Long courseId;

    private Long lessonId;

    private Date scheduledDateTime;

    private Date createTime;

    private Date updateTime;

    private Integer delFlag;

}