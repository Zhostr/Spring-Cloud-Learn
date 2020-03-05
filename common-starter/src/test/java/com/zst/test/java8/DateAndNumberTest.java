package com.zst.test.java8;

import com.zst.commons.util.CommonDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
@Slf4j
public class DateAndNumberTest {

    @Test
    void calenderTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        System.out.println(new Date());
        System.out.println(time);
    }

    @Test
    void bigDecimalTest() {
        //Math.round 四舍五入，忽略正负号，数字部分直接
        float x1 = -6.55f;
        int round = Math.round(x1);
        log.info("round = {}", round);

        //ROUND_CEILING、ROUND_FLOOR 区分正负
        log.info("10/3  ROUND_CEILING  = {}", new BigDecimal(10).divide(new BigDecimal(3), 2, BigDecimal.ROUND_CEILING));
        log.info("-10/3 ROUND_FLOOR    = {}", new BigDecimal(-10).divide(new BigDecimal(3), 2, BigDecimal.ROUND_FLOOR));

        //ROUND_DOWN、ROUND_UP 直接舍入/ +1
        log.info("10/3  ROUND_DOWN = {}", new BigDecimal(10).divide(new BigDecimal(3), 2, BigDecimal.ROUND_DOWN));
        log.info("-10/3 ROUND_DOWN = {}", new BigDecimal(-10).divide(new BigDecimal(3), 2, BigDecimal.ROUND_UP));

        //四舍五入
        log.info("20/3 ROUND_HALF_DOWN = {}", new BigDecimal(20).divide(new BigDecimal(3), 3, BigDecimal.ROUND_HALF_DOWN));
        log.info("20/3 ROUND_HALF_UP = {}", new BigDecimal(20).divide(new BigDecimal(3), 3, BigDecimal.ROUND_HALF_UP));

        //比较运算
        log.info("1+2/8(ROUND_HALF_UP) = {}", new BigDecimal(1).add(new BigDecimal(2)).divide(new BigDecimal(8), 4, BigDecimal.ROUND_HALF_UP));//.compareTo(new BigDecimal("0.125")));

        BigDecimal bigDecimal = new BigDecimal(3.3);//实际是 3.29999999999999982236431605997495353221893310546875
        BigDecimal bigDecimalStr = new BigDecimal("3.3");//这个才是 3.3
        boolean equals = bigDecimal.equals(bigDecimalStr);//false
        log.info("{}.equals({}) ? {}", bigDecimal, bigDecimalStr, equals);


    }



}