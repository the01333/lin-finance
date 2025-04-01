package com.puxinxiaolin.common.util;

import com.puxinxiaolin.common.exception.BizException;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 日期 Util
 * @Author: YCcLin
 * @Date: 2025/4/1 22:11
 */
public class DateUtil {

    /**
     * 获取当前系统时间戳（秒级）
     *
     * @return
     */
    public static long getSecondTimeStamp() {
        return getSecondTimeStamp(LocalDateTime.now());
    }

    /**
     * 获取时间戳（秒级）
     *
     * @param localDateTime
     * @return
     */
    public static long getSecondTimeStamp(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate 转 Date
     *
     * @param localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Date 转 LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static Date getSystemTime() {
        return toDate(LocalDateTime.now());
    }

    /**
     * 获取过期时间时间戳
     *
     * @param expire
     * @param timeUnit
     * @return
     */
    public static Long getExpireTimeStamp(int expire, TimeUnit timeUnit) {
        switch (timeUnit) {
            case NANOSECONDS:
                return DateUtil.getSecondTimeStamp(LocalDateTime.now().plusNanos(expire));
            case MICROSECONDS:
                return DateUtil.getSecondTimeStamp(LocalDateTime.now().plus(expire, ChronoUnit.MICROS));
            case MILLISECONDS:
                return DateUtil.getSecondTimeStamp(LocalDateTime.now().plus(expire, ChronoUnit.MILLIS));
            case SECONDS:
                return DateUtil.getSecondTimeStamp(LocalDateTime.now().plusSeconds(expire));
            case MINUTES:
                return DateUtil.getSecondTimeStamp(LocalDateTime.now().plusMinutes(expire));
            case HOURS:
                return DateUtil.getSecondTimeStamp(LocalDateTime.now().plusHours(expire));
            case DAYS:
                return DateUtil.getSecondTimeStamp(LocalDateTime.now().plus(expire, ChronoUnit.HALF_DAYS));
            default:
                throw new BizException("获取过期日期异常");
        }
    }

    /**
     * 获取两个时间间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Duration getDuration(Date date1, Date date2) {
        return Duration.between(toLocalDateTime(date1), toLocalDateTime(date2));
    }

}
