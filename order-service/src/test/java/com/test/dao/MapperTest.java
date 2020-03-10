package com.test.dao;

import com.zst.order.OrderApplication;
import com.zst.order.entity.Account;
import com.zst.order.mapper.AccountMapper;
import com.zst.order.mapper.OnlineClassMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/02/11 下午2:35
 * @version: V1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MapperTest {

    @Autowired
    private OnlineClassMapper onlineClassMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testInsertMap() {
        Map<String, Object> columnNameAndValue = new HashMap<>(3);
        columnNameAndValue.put("username", "name666");
        columnNameAndValue.put("account", 500);
        int transaction_test = onlineClassMapper.insertMap("transaction_test", columnNameAndValue);
        System.out.println(transaction_test);


        Set<Long> allDistinctLessonId = onlineClassMapper.getAllDistinctLessonId();
        log.info("allDistinctLessonId = {}", allDistinctLessonId);
    }

    @Test
    public void insertTransaction() {
        int result = accountMapper.insert("李四666", 1000, new Date());
        System.out.println(result);
    }

    @Test
    public void testSelectAll() {
        List<Account> accounts = accountMapper.selectAll();
        log.info("list = {}", accounts);
    }



}