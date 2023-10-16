package com.c88.sms.impl;

import com.c88.common.core.util.HttpUtil;
import com.c88.common.core.vo.SendSmsOtpRequestVo;
import com.c88.sms.SmsProvider;
import com.c88.sms.SmsResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component("OnSmsVoice")
public class OnSmsVoiceProvider implements SmsProvider {

    @Value(value = "${sms.otp.provider.1.apiurl}")
    private String apiurl;
    @Value(value = "${sms.otp.provider.1.sender}")
    private String sender;
    @Value(value = "${sms.otp.provider.1.key}")
    private String key;
    @Value(value = "${sms.otp.provider.1.username}")
    private String username;
    @Value(value = "${sms.otp.provider.1.pass}")
    private String pass;

    @Override
    public SmsResultEnum sendSms(String phone, String random5digit) {
        String url = apiurl + "/postSMS";
        SendSmsOtpRequestVo request = new SendSmsOtpRequestVo();
        request.setSender(sender);
        request.setKey(key);
        request.setUsername(username);
        request.setPass(pass);
        request.setPhonesend(phone);
        request.setMessage(String.format(random5digit));

        try {
            log.info("OnSmsVoice_request {} {}", url, request);
            String response = HttpUtil.postByJson(url, request, 15);
            log.info("OnSmsVoice_Response: {}", response);
            if("-4".equals(response)){
                return SmsResultEnum.MOBILE_ERROR;
            }
            String[] resArray = StringUtils.split(response, ";;;");

            if(resArray == null || resArray.length <= 0) {
                log.error("Send SMS Error {}", response);
                throw new Exception(response);
            }
            switch(resArray[0]) {
                case "1":
                    return SmsResultEnum.SUCCESS;
                case "-4":
                    return SmsResultEnum.MOBILE_ERROR;
                default:
                    log.error("Send SMS Error Res: {}", response);
                    return SmsResultEnum.UNKNOWN;
            }
        } catch (Exception e) {
            log.error("Send SMS Runtime Exception {}", ExceptionUtils.getMessage(e));
            return SmsResultEnum.UNKNOWN;
        }
    }

}
