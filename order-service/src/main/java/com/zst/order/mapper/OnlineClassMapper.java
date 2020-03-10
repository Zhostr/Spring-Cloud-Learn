package com.zst.order.mapper;

import com.zst.order.entity.OnlineClassEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/03 下午4:23
 * @version: V1.0
 */
public interface OnlineClassMapper {

    /**
     * select 全部
     * @return
     */
    List<OnlineClassEntity> getAllOnlineClass();

    /**
     * 插入数据，在那个表插入都行
     * @param tableName
     * @param columns
     * @return
     */
    int insertMap(@Param("tableName") String tableName, @Param("columns") Map<String, Object> columns);

    Set<Long> getAllDistinctLessonId();

}