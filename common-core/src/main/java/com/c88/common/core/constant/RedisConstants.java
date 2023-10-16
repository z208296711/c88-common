package com.c88.common.core.constant;

public interface RedisConstants {

    String BUSINESS_NO_PREFIX = "business_no:";

    /**
     * 优惠券码KEY前缀
     */
    String SMS_COUPON_TEMPLATE_CODE_KEY = "sms_coupon_template_code_";

    /**
     * 用户当前所有可用优惠券key
     */
    String SMS_USER_COUPON_USABLE_KEY = "sms_user_coupon_usable_";

    /**
     * 用户当前所有已使用优惠券key
     */
    String SMS_USER_COUPON_USED_KEY = "sms_user_coupon_used_";

    /**
     * 用户当前所有已过期优惠券key
     */
    String SMS_USER_COUPON_EXPIRED_KEY = "sms_user_coupon_expired_";

    /**
     * Google recaptcha token
     */
    String GOOGLE_RECAPTCHA = "GOOGLE_RECAPTCHA";

    /**
     * 會員修改餘額 lock
     */
    String MEMBER_BALANCE = "MEMBER_BALANCE";

    /**
     * 在線會員
     */
    String ONLINE_MEMBER = "ONLINE_MEMBER";

    /**
     * 被管理員手動凍結會員，因為 JWT token 還沒 expire，需要記錄這些會員，已排除被凍結前生成的合法 token
     */
    String FREEZE_MEMBER = "FREEZE_MEMBER";

    String RECEIVE_LEVEL_UP_GIFT = "receive_level_up_gift";

    /**
     * 領取生日禮金Lock
     */
    String RECEIVE_BIRTHDAY_GIFT = "receive_birthday_gift";

    String RECEIVE_FREE_BET_GIFT = "receive_free_bet_gift";

    String VIP_LEVEL_DOWN_SCHEDULE_LOCK = "vip_level_down_schedule_lock";

    String GIFT_NOTIFICATION = "gift_notification";

    String VIP_LEVEL_UP_NOTIFICATION = "vip_level_up_notification";

    String VIP_LEVEL_UP_REWARD_NOTIFICATION = "vip_level_up_reward_notification";

    String VALID_BET_LOCK = "validBetLock";

    String DRAW_LOCK = "draw_lock";

    /**
     * 白菜紅包Lock
     */
    String CHINESE_CABBAGE_LOCK = "chineseCabbageLock";

    /**
     * 紅包代碼Lock
     */
    String RED_ENVELOPE_CODE_LOCK = "redEnvelopeCodeLock";

}
