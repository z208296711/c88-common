package com.c88.sms.impl;

import com.c88.common.core.util.GameUtil;
import com.c88.common.core.util.HttpUtil;
import com.c88.common.core.vo.SendSmsNuageRequestVo;
import com.c88.common.core.vo.SendSmsNuageResponseVo;
import com.c88.sms.SmsProvider;
import com.c88.sms.SmsResultEnum;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component("Nuage")
public class NuageProvider implements SmsProvider {

    @Value(value = "${sms.otp.provider.nuage.apiurl}")
    private String apiurl;
    @Value(value = "${sms.otp.provider.nuage.apiKey}")
    private String apiKey;
    @Value(value = "${sms.otp.provider.nuage.name}")
    private String name;
    @Value(value = "${sms.otp.provider.nuage.pwd}")
    private String pwd;

    @Override
    public SmsResultEnum sendSms(String phone, String random5digit) {
        String msg = String.format("[comebet]Your verify code is %s", random5digit);
        SendSmsNuageRequestVo request = new SendSmsNuageRequestVo();
        if (phone.startsWith("0")) {
            phone = "84" + phone.substring(1);
        }

        request.setReceiver(phone);
        request.setMsg(msg);
        request.setName(name);
        request.setPwd(pwd);

        StringBuilder sb = new StringBuilder()
                .append(msg)
                .append(phone)
                .append(apiKey);
        request.setChecksum(GameUtil.getMD5(sb.toString()));

        try {
            log.info("Nuage_request {} {}", apiurl, request);
            String response = HttpUtil.doGet(apiurl, "", request);
            log.info("Nuage_Response: {}", response);
            SendSmsNuageResponseVo resObj = new Gson().fromJson(response, SendSmsNuageResponseVo.class);

            if (resObj.getIsSuccess()) {
                return SmsResultEnum.SUCCESS;
            } else if (resObj.getMessage().contains("NotAvailable")) {
                return SmsResultEnum.MOBILE_ERROR;
            } else {
                log.error("Send SMS Error Res: {}", response);
                return SmsResultEnum.UNKNOWN;
            }
        } catch (Exception e) {
            log.error("Send SMS Runtime Exception {}", ExceptionUtils.getMessage(e));
            return SmsResultEnum.UNKNOWN;
        }
    }
}
