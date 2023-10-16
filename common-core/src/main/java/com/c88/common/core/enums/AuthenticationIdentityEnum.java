package com.c88.common.core.enums;

import com.c88.common.core.base.IBaseEnum;
import lombok.Getter;

public enum AuthenticationIdentityEnum implements IBaseEnum<String> {

    USERNAME("username", "用户名"),

    MOBILE("mobile", "手机号"),

    OPENID("openId", "开放式认证系统唯一身份标识");

    @Getter
    private String value;

    @Getter
    private String label;

    AuthenticationIdentityEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
