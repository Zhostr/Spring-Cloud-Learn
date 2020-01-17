package com.zst.test.java8;

import com.zst.commons.util.CommonDateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

/**
 * @description:
 * @author: Zhoust
 * @date: 2020/01/08 下午2:53
 * @version: V1.0
 */
public class DateTest {

    @Test
    void intStreamTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        System.out.println(new Date());
        System.out.println(time);
    }


}