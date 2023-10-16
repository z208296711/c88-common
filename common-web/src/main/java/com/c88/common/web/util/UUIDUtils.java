package com.c88.common.web.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UUIDUtils {

    public static String genOrderId(String prefix) {
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return prefix + sdf.format(date) + Long.toString(currentTime).substring(2, 13);
    }

    /**
     * 產生prefix+年月日時分秒（14碼）+5碼隨機數
     *
     * @param prefix
     * @return
     */
    private static String genTimeOrderId(String prefix) {
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return prefix + sdf.format(date) + Long.toString(currentTime).substring(8, 13);
    }

    public static String genInlineOrderId() {
        return genOrderId("RK");
    }

    public static String genOnlineOrderId() {
        return genOrderId("SF");
    }

    /**
     * 產生提款訂單號
     *
     * @return
     */
    public static String genWithdrawOrderId() {
        return genTimeOrderId("WD");
    }
}
