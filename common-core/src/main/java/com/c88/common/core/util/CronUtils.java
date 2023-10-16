package com.c88.common.core.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class CronUtils {


    /**
     * 每天时间format格式
     */
    public static final String DATEFORMAT_EVERYDAY = "ss mm HH * * ?";

    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }


    /**
     * 时间转换时间表达式
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2020"
     *
     * @param date: 时间点
     * @param dateFormat
     * @return
     */
    public static String getCron(Date date, String dateFormat) {
        return formatDateByPattern(date, dateFormat);
    }




    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        Date date = Date.from(zdt.toInstant());

        String cron1 = getCron(date, DATEFORMAT_EVERYDAY);
        System.out.println(cron1);
    }

}
