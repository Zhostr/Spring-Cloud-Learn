package com.zst.commons.util;

import com.vip.vjtools.vjkit.base.annotation.NotNull;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 通用 Date 工具类（线程安全），@NotNull 只作为标记表明该入参不能为 null，使用时要先 check
 * @author: Zhoust
 * @date: 2019/09/11 15:35
 * @version: V1.0
 */
@UtilityClass
public class CommonDateUtil extends DateFormatUtil {


    //==================== String -> Date ====================
    /**
     * String -> Date（格式：yyyy-MM-dd HH:mm:ss）
     * @see DateFormatUtil#formatDate(String, Date)
     * @param dateStr
     */
    @SneakyThrows(value = ParseException.class)
    public Date parseDateOnSecond(@NotNull String dateStr) {
        return DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(dateStr);
    }

    /**
     * String -> Date（格式：yyyy-MM-dd HH:mm:ss.SSS）
     * @param dateStr
     * @return
     */
    @SneakyThrows(value = ParseException.class)
    public Date parseDateOnMilliSecond(@NotNull String dateStr) {
        return DateFormatUtil.DEFAULT_FORMAT.parse(dateStr);
    }

    //==================== Date -> String ====================
    /**
     * Date -> String（格式：yyyy-MM-dd HH:mm:ss）
     * @param date
     * @return
     */
    public String formatDateOnSecond(@NotNull Date date) {
        return DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(date);
    }

    /**
     * Date -> String（格式：yyyy-MM-dd HH:mm:ss.SSS）
     * @param date
     * @return
     */
    public String formatDateOnMilliSecond(@NotNull Date date) {
        return DateFormatUtil.DEFAULT_FORMAT.format(date);
    }


    //==================== 日期类常用计算 ====================

    /**
     * 计算 date +/- interval 的时间
     * @param date
     * @param interval
     * @param unit ChronoUnit 枚举值
     * @return
     */
    public Date calculateInterval(@NotNull Date date, @NotNull Long interval, @NotNull TemporalUnit unit) {
        return Date.from(date.toInstant().plus(Duration.of(interval, unit)));
    }


}