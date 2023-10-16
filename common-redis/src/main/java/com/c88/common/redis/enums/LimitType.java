package com.c88.common.redis.enums;

public enum LimitType {
    /**
     * 自定義key
     */
    CUSTOM,
    /**
     * 根據請求者IP
     */
    IP,
    /**
     * userId
     */
    LOGIN_MEMBER,

    BALANCE,

    PLATFORM_TRANSFER,
    /**
     * 自定義key + userId
     */
    CUSTOM_AND_LOGIN_MEMBER;
}
