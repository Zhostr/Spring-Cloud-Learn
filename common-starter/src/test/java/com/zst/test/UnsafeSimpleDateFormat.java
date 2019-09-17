package com.zst.test;

import com.zst.commons.util.CommonDateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        for (int i = 0; i< 3; i++) {
            new TestSimpleDateFormatThreadSafe().start();
        }
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