package com.c88.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ReviewEnum {

    NOT_REVIEW(0, "不需審核"),
    NEED_REVIEW(1, "需審核");

    private final Integer code;

    private final String desc;

    public static ReviewEnum getEnum(Integer code) {
        return Arrays.stream(values()).filter(filter -> Objects.equals(filter.getCode(), code)).findFirst().orElseThrow();
    }
}
