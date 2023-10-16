package com.c88.sms;

public interface SmsProvider {

    SmsResultEnum sendSms(String phone, String random5digit);

}
