package com.c88.common.redis.constant;

import com.c88.common.redis.utils.RedisUtils;

import java.time.LocalDate;
import java.util.Calendar;

public class RedisKey {

    private RedisKey() {
        throw new IllegalStateException("Utility class");
    }

    public static final String RECHARGE_AWARD_BY_DAY = "rechargeAwardByDay";
    public static final String RECHARGE_AWARD_BY_WEEK = "rechargeAwardByWeek";
    public static final String RECHARGE_AWARD_BY_MONTH = "rechargeAwardByMonth";
    public static final String RECHARGE_AWARD_BY_TOTAL = "rechargeAwardByTotal";

    /**
     * 累積充值要求Key
     *
     * @param templateId 模組ID
     * @param memberId   會員ID
     * @return
     */
    public static String getRechargeAwardByTotalKey(Long templateId, Long memberId) {
        return RedisUtils.buildKey(RedisKey.RECHARGE_AWARD_BY_TOTAL, templateId, memberId);
    }

    /**
     * 每月次數Key
     *
     * @param nowDate    日期
     * @param templateId 模組ID
     * @param memberId   會員ID
     * @return
     */
    public static String getRechargeAwardByMonthKey(LocalDate nowDate, Long templateId, Long memberId) {
        return RedisUtils.buildKey(RedisKey.RECHARGE_AWARD_BY_MONTH, nowDate.getMonthValue(), templateId, memberId);
    }

    /**
     * 每週次數Key
     *
     * @param templateId 模組ID
     * @param memberId   會員ID
     * @return
     */
    public static String getRechargeAwardByWeekKey(Long templateId, Long memberId) {
        return RedisUtils.buildKey(RedisKey.RECHARGE_AWARD_BY_WEEK, Calendar.getInstance().get(Calendar.WEEK_OF_YEAR), templateId, memberId);
    }

    /**
     * 每日次數Key
     *
     * @param nowDate    日期
     * @param templateId 模組ID
     * @param memberId   會員ID
     * @return
     */
    public static String getRechargeAwardByDayKey(LocalDate nowDate, Long templateId, Long memberId) {
        return RedisUtils.buildKey(RedisKey.RECHARGE_AWARD_BY_DAY, nowDate, templateId, memberId);
    }

}
