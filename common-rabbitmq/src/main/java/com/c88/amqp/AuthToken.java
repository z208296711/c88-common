package com.c88.amqp;

import lombok.Data;

@Data
public class AuthToken {
    long id;
    String principal;
    String credentials;
    boolean isSms;
    String userName;
    String ip;
    String deviceCode;
    String device;

    String area;

    String loginDomain;
}
