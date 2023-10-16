package com.c88.common.core.enums;


import com.c88.common.core.base.IBaseEnum;
import lombok.Getter;

public enum BusinessTypeEnum implements IBaseEnum<Integer> {

    USER(100, "用户"),
    MEMBER(200, "会员"),
    ORDER(300, "订单");

    @Getter
    private Integer value;

    @Getter
    private String label;

    BusinessTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
