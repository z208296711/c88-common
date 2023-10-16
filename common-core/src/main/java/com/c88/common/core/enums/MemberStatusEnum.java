package com.c88.common.core.enums;

import lombok.Getter;

import java.util.Arrays;


public enum MemberStatusEnum {

    ENABLE((byte) 1),// 啟用
    FREEZE((byte) 2);// 凍結

    @Getter
    private byte status;

    MemberStatusEnum(byte status) {
        this.status = status;
    }

    public static MemberStatusEnum getEnum(byte status) {
        return Arrays.stream(values()).filter(e -> e.status == status).findFirst().get();
    }

}
