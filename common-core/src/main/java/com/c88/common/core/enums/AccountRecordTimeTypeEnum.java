package com.c88.common.core.enums;

import com.c88.common.core.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * 帳務紀錄時間類型
 */
@Getter
@AllArgsConstructor
public enum AccountRecordTimeTypeEnum {

    TODAY(1, "今日"),
    YESTERDAY(2, "昨天"),
    WITHIN_SEVEN_DAYS(3, "七天內"),
    WITHIN_THIRTY_DAYS(4, "三十天內");

    private final Integer code;

    private final String label;

    public static AccountRecordTimeTypeEnum getEnum(Integer code) {
        return Arrays.stream(values()).filter(filter -> Objects.equals(filter.getCode(), code)).findFirst().orElseThrow();
    }

    public static LocalDateTime getStartDateTime(Integer code) {
        switch (AccountRecordTimeTypeEnum.getEnum(code)) {
            case TODAY:
                return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            case YESTERDAY:
                return LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN);
            case WITHIN_SEVEN_DAYS:
                return LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIN);
            case WITHIN_THIRTY_DAYS:
            default:
                return LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.MIN);
        }

    }

    public static LocalDateTime getEndDateTime(Integer code) {
        switch (AccountRecordTimeTypeEnum.getEnum(code)) {
            case YESTERDAY:
                return LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);
            case TODAY:
            case WITHIN_SEVEN_DAYS:
            case WITHIN_THIRTY_DAYS:
            default:
                return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        }
    }

}
