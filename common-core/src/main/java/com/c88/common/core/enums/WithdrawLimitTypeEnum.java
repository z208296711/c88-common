package com.c88.common.core.enums;

import com.c88.common.core.base.IBaseEnum;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum WithdrawLimitTypeEnum implements IBaseEnum<Integer> {

    DEDUCT_VALID_BET(-1, "扣除打碼量"),
    NON(0, "無"),
    BIRTHDAY_GIFT(1, "優惠-生日禮金"),
    LEVEL_UP_GIFT(2, "優惠-晉級禮金"),
    FREE_BET_WEEKLY_GIFT(3, "優惠-週禮金"),
    FREE_BET_MONTHLY_GIFT(4, "優惠-月禮金"),
    RECHARGE(5, "充值"),
    CHINESE_CABBAGE_RED_ENVELOPE(6, "白菜紅包"),
    RED_ENVELOPE_CODE(7, "紅包代碼"),
    COMMON_DRAW(8, "一般抽獎"),





    NOT_USE(99999, "佔位請勿使用");

    @Getter
    private final Integer value;

    @Getter
    private final String label;

    WithdrawLimitTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<Integer, WithdrawLimitTypeEnum> map = Stream.of(values())
            .collect(Collectors.toMap(WithdrawLimitTypeEnum::getValue, Function.identity()));

    public static WithdrawLimitTypeEnum fromIntValue(int value) {
        return map.get(value);
    }


}
