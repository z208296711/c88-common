package com.c88.common.core.vo;

import lombok.Data;

@Data
public class SendSmsOtpRequestVo {

    private String sender;
    private String key;
    private String pass;
    private String username;
    private String phonesend;
    private String message;

}
