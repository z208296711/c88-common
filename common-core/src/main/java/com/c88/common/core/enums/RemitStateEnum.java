package com.c88.common.core.enums;

import com.c88.common.core.base.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;


public enum RemitStateEnum implements IBaseEnum {

    APPLY((byte) 0, "待領取"),
    PENDING((byte) 1, "人工出款中"),
    SUCCESS((byte) 2, "人工出款成功"),
    CANCEL((byte) 3, "人工取消出款"),
    PAY_SUCCESS((byte) 4, "自動代付成功"),
    PAY_PENDING((byte) 5, "代付出款中"),
    PAY_FAILED((byte) 6, "自動代付失敗"),
    PAY_FAILED_REAL_NAME((byte) 7, "自動代付失敗（實名失敗）"),
    REVOKED((byte) 8, "撤銷出款");

    @Getter
    private Byte value;

    @Getter
    private String desc;

    RemitStateEnum(byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static RemitStateEnum getEnum(byte state) {
        return Arrays.stream(values()).filter(e -> e.value == state).findFirst().get();
    }

    public Byte getState() {
        return value;
    }

    @Override
    public String getLabel() {
        return null;
    }
}
