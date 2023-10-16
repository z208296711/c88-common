package com.c88.common.core.util;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;

import static java.time.ZoneId.SHORT_IDS;

@Slf4j
public class DateUtil {

    public static final DateTimeFormatter ym_df = DateTimeFormatter.ofPattern("yyyy-MM");

    public static final String ymdhms = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime strToFirstDayOfMonthWithLocalDateTime(String str){

        DateTimeFormatter DATEFORMATTER = new DateTimeFormatterBuilder().append(ym_df)
                .parseDefaulting(ChronoField.DAY_OF_MONTH,1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        //DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime ldt = LocalDateTime.parse(str, DATEFORMATTER);

        return ldt;
    }

    /**
     * 从时间戳转换成 LocalDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime timestamp(Long timestamp) {
        Date date = new Date(timestamp);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(SHORT_IDS.get("CTT")));
    }

    /**
     * 以second为主进行判断
     * 以first为开始时间，second为结束时间
     * 判断两个时间是否在通过一个周内。
     *
     * @param first
     * @param second
     */
    public static Boolean checkWeekBetween(LocalDateTime first, LocalDateTime second) {
//        两个时间差不超过7天,
        Period period = Period.between(first.toLocalDate(), second.toLocalDate());
        int years = period.getYears();
        log.info("years:{}", years);
        if (years > 0) {
            return false;
        }
        int months = period.getMonths();
        log.info("months:{}", months);
        if (months > 0) {
            return false;
        }
        int days = period.getDays();
        log.info("days:{}", days);
        if (days == 0) {
//          表明是同一天
            return true;
        }
        if (days > 7 || days < -7) {
//            两个时间差 超出了7天
            return false;
        }
        int firstDayOfWeek = first.getDayOfWeek().getValue();
        int secondDayOfWeek = second.getDayOfWeek().getValue();
        if (secondDayOfWeek == 1) {
            if (oneDay(firstDayOfWeek, secondDayOfWeek, days)) {
                return true;
            } else {
                return false;
            }
        }
        if (secondDayOfWeek == 7) {
            if (sevenDay(firstDayOfWeek, secondDayOfWeek, days)) {
                return true;
            } else {
                return false;
            }
        }
        if (otherDay(firstDayOfWeek, secondDayOfWeek, days)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * secondDayOfWeek 是所在星期的第一天
     * 星期的第一天 数据处理
     *
     * @return
     */
    public static Boolean oneDay(int firstDayOfWeek, int secondDayOfWeek, int days) {
        if (days > 0) {
//            表明 first 比second 小 不在同一周
            return false;
        } else {
//            表明 first 比second 大
            if (secondDayOfWeek - days == firstDayOfWeek) {
                return true;
            }
        }
        return false;
    }

    /**
     * 星期的第7天的时候处理数据
     *
     * @return
     */
    public static Boolean sevenDay(int firstDayOfWeek, int secondDayOfWeek, int days) {
//        second 是周日
        if (firstDayOfWeek + days == secondDayOfWeek) {
            return true;
        }
        return false;
    }

    /**
     * 其他天的数据处理
     *
     * @return
     */
    public static Boolean otherDay(int firstDayOfWeek, int secondDayOfWeek, int days) {
        if (days < 0) {
//              表明 first 比 second 大
            if ((secondDayOfWeek - days) == firstDayOfWeek) {
//                    两者是 一周内
                return true;
            }
        } else {
//          表明 first 比 second 小
            if (firstDayOfWeek + days == secondDayOfWeek) {
                //                    两者是 一周内
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime second = LocalDateTime.parse("2016-12-31 14:18:20", formatter);
        LocalDateTime first = LocalDateTime.parse("2017-01-01 14:18:20", formatter);
//        以second 为准 判断是否在 同一周内
        log.info("firstDay:{},secondDay:{};是否是同一周：{}", first, second, checkWeekBetween(first, second));
        System.exit(0);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.of("+7"));
    }
    public static LocalDateTime getStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTime.with(LocalTime.MIN);
    }

    public static LocalDateTime getEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return endOfDay;
    }

    public static LocalDateTime[] getTodayFloorAndCelling(Date date){
        LocalDateTime start = getStartOfDay(date);
        LocalDateTime end = getEndOfDay(date);

        LocalDateTime[] dates = new LocalDateTime[2];
        dates[0] = start;
        dates[1] = end;

//        Date[] dates = new Date[2];
//        dates[0] = java.sql.Timestamp.valueOf(start);
//        dates[1] = java.sql.Timestamp.valueOf(end);

        return dates;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDate2 = date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    public static String dateToStr(LocalDateTime start, String formatLinkDay) {
        return start.format(DateTimeFormatter.ofPattern(formatLinkDay));
    }

}
