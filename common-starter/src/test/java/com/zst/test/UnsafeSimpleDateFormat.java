package com.zst.test;

import com.vip.vjtools.vjkit.time.DateUtil;
import com.zst.commons.util.CommonDateUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: SimpleDateFormat 线程安全测试
 * @author: Zhoust
 * @date: 2019/09/12 20:07
 * @version: V1.0
 */
public class UnsafeSimpleDateFormat {

    static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(CommonDateUtil.PATTERN_DEFAULT_ON_SECOND);

    public static void main(String[] args) {
        LocalDate nowLocalDate = LocalDate.now();
        Instant nowInstant = nowLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Instant plus = nowInstant.plus(10L, ChronoUnit.HOURS);
        System.out.println(Date.from(plus));
//        for (int i = 0; i< 3; i++) {
//            new TestSimpleDateFormatThreadSafe().start();
//        }
    }

    public static class TestSimpleDateFormatThreadSafe extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    this.join(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.out.println(CommonDateUtil.formatDateOnSecond(new Date()));
//                System.out.println(SIMPLE_DATE_FORMAT.format(new Date()));
                /*try {
                    System.out.println(this.getName()+":"+SIMPLE_DATE_FORMAT.parse("2013-05-24 06:02:20"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
            }
        }
    }

}