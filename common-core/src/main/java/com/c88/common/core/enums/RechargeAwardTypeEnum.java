package com.c88.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 存送優惠類型
 */
@Getter
@AllArgsConstructor
public enum RechargeAwardTypeEnum {

    PLATFORM(1, "平台"),
    PERSONAL(2, "個人");

    private final Integer code;

    private final String desc;

    public static RechargeAwardTypeEnum getEnum(Integer code) {
        return Arrays.stream(values()).filter(filter -> Objects.equals(filter.getCode(), code)).findFirst().orElseThrow();
    }



}
