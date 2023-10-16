package com.c88.common.core.enums;


import com.c88.common.core.base.IBaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 風控維度
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(implementation = RiskTypeSer.class)
public enum RiskTypeEnum implements IBaseEnum<Byte> {

    WITHDRAW_RECHARGE_RATIO((byte) 1, "充提比", "risk_set_post.Item01"),
    REWARD_BET((byte) 2, "投注中奖倍数", "risk_set_post.Item02"),
    HUGE_WITHDRAW((byte) 3, "大额提款", "risk_set_post.Item03"),
    EARNINGS((byte) 4, "当日盈利", "risk_set_post.Item04"),
    RELATE((byte) 5, "关联异常", "risk_set_post.Item05", "risk.relateWarning"),
    BALANCE_ABNORMAL((byte) 6, "资金异常", "risk_set_post.Item06"),
    FIRST_WITHDRAW((byte) 7, "首提用户", "risk_set_post.Item07"),
    FIRST_WITHDRAW_AFTER_UPDATED((byte) 8, "修改后首提", "risk_set_post.Item08"),
    NO_TURNOVER_WITHDRAW((byte) 9, "无流水提款", "risk_set_post.Item09"),
    TAG((byte) 10, "异常标签", "risk_set_post.Item10", "risk.tagWarning");

    @Getter
    private Byte value;

    @Getter
    private String desc;

    @Getter
    private String code;

    @JsonIgnore
    private String warningCode;// 風控維度警示語 i18n

    RiskTypeEnum(Byte value, String desc, String code) {
        this.value = value;
        this.desc = desc;
        this.code = code;
    }

    RiskTypeEnum(Byte value, String desc, String code, String warningCode) {
        this.value = value;
        this.desc = desc;
        this.code = code;
        this.warningCode = warningCode;
    }

    public String getWarningCode() {
        if (this.warningCode != null) {
            return this.warningCode;
        }
        return this.code;
    }

    @JsonIgnore
    @Override
    public String getLabel() {
        return code;
    }

}

@Getter
class RiskTypeSer {// for spring doc use
    @Schema(title = "風控維度值")
    Byte value;
    @Schema(title = "風控維度 i18n")
    String code;
}