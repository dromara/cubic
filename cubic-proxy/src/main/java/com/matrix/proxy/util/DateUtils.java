package com.matrix.proxy.util;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * 日期计算模式
 *
 * @author QIANGLU on 2019/9/11
 */
public class DateUtils {


    public static final ZoneId ASIA_SHANGHAI = ZoneId.of("Asia/Shanghai");


    /**
     * 计算心跳
     */
    public static long heartTime(long heart) {
        Date heartTime = new Date(heart);
        LocalDateTime.ofInstant(heartTime.toInstant(), ASIA_SHANGHAI);
        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.ofInstant(heartTime.toInstant(), ASIA_SHANGHAI), LocalDateTime.now(ASIA_SHANGHAI));

        return seconds;
    }


    /**
     * 计算时间差
     */
    public static String calculateTimeDifference(LocalDateTime source) {

        long seconds = ChronoUnit.SECONDS.between(source, LocalDateTime.now(ASIA_SHANGHAI));

        StringBuilder builder = new StringBuilder();

        long day = seconds / (60 * 60 * 24);
        if (day != 0) {
            builder.append(day).append(" 天").append(" ");
        }
        long hour = (seconds / (60 * 60)) - (day * 24);
        if (hour != 0) {
            builder.append(hour).append(" 小时").append(" ");
        }
        long min = (seconds / 60) - (hour * 60) - (day * 24 * 60);
        if (min != 0) {
            builder.append(min).append(" 分").append(" ");
        }
        long sec = seconds - (hour * 60 * 60) - (min * 60) - (day * 24 * 60 * 60);
        if (sec != 0) {
            builder.append(sec).append(" 秒");
        }
        return builder.toString();
    }

    public static String calculateTime(LocalDateTime source) {

        long seconds = ChronoUnit.SECONDS.between(source, LocalDateTime.now(ASIA_SHANGHAI));
        if (seconds > 300) {
            return "offline";
        }
        return calculateTimeDifference(source) + "前";
    }

    public static LocalDate dateTime2Date(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();

    }

    public static LocalDateTime getTime(String endTime) {

        return getTimeByPattern(endTime,"yyyy-MM-dd HH:mm:ss");
    }

    public static LocalDateTime getTimeByPattern(String endTime,String pattern) {
        LocalDateTime localDate = LocalDateTime.now();
        if (StringUtils.isNotEmpty(endTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            localDate = DateUtils.str2Date(endTime, formatter);
        }

        return localDate;
    }


    public static Date localDateToDate(LocalDate localDate) {
        ZonedDateTime zdt = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());

    }

    public static Date stringToDate(String strDate) {
        return stringToDate(strDate,"yyyy-MM-dd HH:mm:ss");
    }

    public static Date stringToDate(String strDate,String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(strDate, formatter);
        ZonedDateTime zdt = localDateTime.atZone(ASIA_SHANGHAI);
        return Date.from(zdt.toInstant());
    }

    /**
     * 计算minutes分钟前的时间
     *
     * @param strDate
     * @param minutes
     * @return
     */
    public static Date stringToDate(String strDate, long minutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(strDate, formatter);
        localDateTime.minusMinutes(minutes);
        ZonedDateTime zdt = localDateTime.atZone(ASIA_SHANGHAI);
        return Date.from(zdt.toInstant());
    }


    /**
     * 日期对象转换为日期对象
     *
     * @param localDate 日期对象
     * @return 日期时间对象
     */
    public static LocalDateTime dateToDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.NOON);
    }


    /**
     * 字符串转换为日期
     *
     * @param strDate 字符串日期
     * @return 日期对象 yyyy-mm-dd
     */
    public static LocalDate str2Date(String strDate) {
        return LocalDate.parse(strDate, DateTimeFormatter.ISO_DATE);
    }

    public static LocalDateTime str2Date(String strDate, DateTimeFormatter formatter) {
        return LocalDateTime.parse(strDate, formatter);
    }

    /**
     * 日期对象转换为字符串
     *
     * @param localDate 日期对象
     * @return 日期字符串 yyyy-mm-dd
     */
    public static String date2Str(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_DATE);
    }


    /**
     * 日期时间对象转换为字符串
     *
     * @param localDateTime     日期时间对象
     * @param dateTimeFormatter 格式化字符串
     * @return 日期字符串
     */
    public static String dateTime2Str(LocalDateTime localDateTime, String dateTimeFormatter) {
        return localDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    /**
     * 日期时间转字符串函数
     * 返回ISO标准的日期字符串
     *
     * @param localDateTime 日期时间对象
     * @return 日期字符串
     */
    public static String dateTime2Str(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    public static int daysBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getDays();
    }

    /**
     * 计算两个日期之间相差的月数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    public static int monthsBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getMonths();
    }

    /**
     * 计算两个日期之间相差的年数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    public static int yearsBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getYears();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    public static int daysBetween(Date date1, Date date2) {
        Instant instantDate1 = date1.toInstant();
        Instant instantDate2 = date2.toInstant();
        LocalDate localDate1 = instantDate1.atZone(ASIA_SHANGHAI).toLocalDate();
        LocalDate localDate2 = instantDate2.atZone(ASIA_SHANGHAI).toLocalDate();
        instantDate1.atZone(ASIA_SHANGHAI);
        Period period = Period.between(localDate1, localDate2);
        return period.getDays();
    }

    /**
     * 计算两个日期之间相差的月数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    public static int monthsBetween(Date date1, Date date2) {
        Instant instantDate1 = date1.toInstant();
        Instant instantDate2 = date2.toInstant();
        LocalDate localDate1 = instantDate1.atZone(ASIA_SHANGHAI).toLocalDate();
        LocalDate localDate2 = instantDate2.atZone(ASIA_SHANGHAI).toLocalDate();
        instantDate1.atZone(ASIA_SHANGHAI);
        Period period = Period.between(localDate1, localDate2);
        return period.getMonths();
    }

    /**
     * 计算两个日期之间相差的年数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     * @return
     */
    public static int yearsBetween(Date date1, Date date2) {
        Instant instantDate1 = date1.toInstant();
        Instant instantDate2 = date2.toInstant();
        LocalDate localDate1 = instantDate1.atZone(ASIA_SHANGHAI).toLocalDate();
        LocalDate localDate2 = instantDate2.atZone(ASIA_SHANGHAI).toLocalDate();
        instantDate1.atZone(ASIA_SHANGHAI);
        Period period = Period.between(localDate1, localDate2);
        return period.getYears();
    }

    /**
     * 获取指定日期对象当前月的起始日
     *
     * @param localDate 指定日期
     * @return
     */
    public static int getFirstDayInMonth(LocalDate localDate) {
        LocalDate result = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return result.getDayOfMonth();

    }

    /**
     * 获取指定日期对象的当前月的结束日
     *
     * @param localDate 指定日期
     * @return
     */
    public static int getLastDayInMonth(LocalDate localDate) {
        LocalDate result = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return result.getDayOfMonth();
    }


    /**
     * 获取指定日期对象本月的某周某天的日期
     *
     * @param localDate  日期对象
     * @param weekNumber 周
     * @param dayNumber  日
     * @return
     */
    public static LocalDate getLocalDateBydayAndWeek(LocalDate localDate, int weekNumber, int dayNumber) {
        return localDate.with(TemporalAdjusters.dayOfWeekInMonth(weekNumber, DayOfWeek.of(dayNumber)));
    }


    public static String getDateFormat(long timestamp) {
        LocalDateTime heartTime = LocalDateTime.ofInstant(new Date(timestamp).toInstant(),  DateUtils.ASIA_SHANGHAI);
        return heartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getDateFormat(Date date) {
        LocalDateTime heartTime = LocalDateTime.ofInstant(date.toInstant(),  DateUtils.ASIA_SHANGHAI);
        return heartTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field) {
        return time.minus(number, field);
    }

}
