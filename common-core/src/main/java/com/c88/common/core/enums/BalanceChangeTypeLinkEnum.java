package com.c88.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum BalanceChangeTypeLinkEnum {

    /**
     * 前99號對應
     */
    RECHARGE(1, 1, "充值", "memberdetails.accountchange_column04_01"),
    WITHDRAW(2, 2, "提款", "memberdetails.accountchange_column04_02"),
    BONUS(3, 3, "紅利", ""),
    ZERO(4, 4, "清零", ""),
    TRANSFER(5, 5, "轉帳", "memberdetails.accountchange_column04_05_01"),
    COMMISSION(6, 6, "佣金", "memberdetails.accountchange_column04_06"),
    ADJUST(7, 7, "手動調整累積要求流水", "memberdetails.accountchange_column04_07"),
    RECHARGE_PROMOTION(8, 8, "存送優惠", ""),

    /**
     * 其他項目
     */

    OUT_RECHARGE(100, 1, "在線充值", "memberdetails.accountchange_column04_01"),
    MAKE_OUT_RECHARGE(101, 1, "補單在線充值", "memberdetails.accountchange_column04_01_02"),
    IN_RECHARGE(102, 1, "手動充值", "memberdetails.accountchange_column04_01_03"),
    MAKE_IN_RECHARGE(103, 1, "補單手動充值", "memberdetails.accountchange_column04_01_04"),

    BIRTHDAY_GIFT(104, 3, "優惠-生日禮金", "memberdetails.accountchange_column04_03_03"),
    LEVEL_UP_GIFT(105, 3, "優惠-晉級禮金", "memberdetails.accountchange_column04_03_05"),
    FREE_BET_WEEKLY_GIFT(106, 3, "優惠-週禮金", "memberdetails.accountchange_column04_03_01"),
    FREE_BET_MONTHLY_GIFT(107, 3, "優惠-月禮金", "memberdetails.accountchange_column04_03_02"),
    CHINESE_CABBAGE_RED_ENVELOPE(108, 3, "白菜紅包", "memberdetails.accountchange_column04_03_04"),
    RED_ENVELOPE_CODE(109, 3, "紅包代碼", "memberdetails.accountchange_column04_03_08"),
    COMMON_DRAW(110, 3, "一般抽獎", "memberdetails.accountchange_column04_03_06"),

    MEMBER_RECHARGE(111, 1, "會員充值", "memberdetails.accountchange_column04_01_02"),
    MANUAL_WITHDRAW(112, 2, "人工出款", "memberdetails.accountchange_column04_02"),
    AUTO_WITHDRAW(113, 2, "自動代付", "memberdetails.accountchange_column04_02_02"),
    WITHDRAW_FAIL(114, 2, "失敗退還", "memberdetails.accountchange_column04_02_03"),
    TRANSFER_INTO_THIRD(115, 5, "平台轉出", "memberdetails.accountchange_column04_05_02"),
    TRANSFER_INTO_PLATFORM(116, 5, "平台轉入", "memberdetails.accountchange_column04_05_01"),
    TRANSFER_FAIL(118, 5, "失敗退還", "memberdetails.accountchange_column04_02_03"),
    REBATE(117, 3, "返水", "memberdetails.accountchange_column04_03_07"),

    NOT_USE(99999, 99999, "佔位請勿使用", "");

    private final Integer code;

    private final Integer type;

    private final String label;

    private final String i18n;

    /**
     * 依照代碼取得
     *
     * @param code
     * @return
     */
    public static BalanceChangeTypeLinkEnum getEnum(Integer code) {
        return Arrays.stream(values()).filter(filter -> Objects.equals(filter.getCode(), code)).findFirst().orElseThrow();
    }

    /**
     * 依照類型取得
     * 排除主項目
     *
     * @param type
     * @return
     */
    public static List<BalanceChangeTypeLinkEnum> getEnumsByType(Integer type) {
        return Arrays.stream(values()).filter(filter -> (!List.of(1, 2, 3, 4, 5, 6, 7, 8).contains(filter.getCode())) && Objects.equals(filter.getType(), type)).collect(Collectors.toList());
    }

}
