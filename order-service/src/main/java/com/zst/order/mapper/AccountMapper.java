package com.zst.order.mapper;

import com.zst.order.entity.Account;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/11 下午4:22
 * @version: V1.0
 */
public interface AccountMapper {

    /**
     * 新增
     * @param userName
     * @param account
     * @param createTime
     * @return
     */
    int insert(@Param("userName") String userName, @Param("account") Integer account, @Param("createTime")Date createTime);

    /**
     *
     * @return
     */
    List<Account> selectAll();


}