package com.c88.common.core.enums;

import com.c88.common.core.base.IBaseEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public enum WithdrawStateEnum implements IBaseEnum {

    APPLY((byte) 0, "待審核"),
    SECOND((byte) 2, "提交二審"),
    APPROVED((byte) 3, "審核通過"),
    REJECTED((byte) 4, "審核失敗");

    @Getter
    private Byte value;

    @Getter
    private String desc;

    WithdrawStateEnum(byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static WithdrawStateEnum getEnum(byte state) {
        return Arrays.stream(values()).filter(e -> e.value == state).findFirst().get();
    }

    /**
     * 取得所有常用備註-訊息分類
     *
     * @return
     */
    public static Set<Byte> allNoteTypes() {
        return Arrays.stream(values())
                .filter(e -> e != APPLY)
                .map(WithdrawStateEnum::getValue)
                .collect(Collectors.toSet());
    }

    public Byte getState(){
        return value;
    }

    @Override
    public String getLabel() {
        return null;
    }
}
