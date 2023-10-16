package com.c88.sms.impl;

import com.c88.common.core.util.GameUtil;
import com.c88.common.core.util.HttpUtil;
import com.c88.common.core.vo.SendSmsAbenlaRequestVo;
import com.c88.common.core.vo.SendSmsAbenlaResponseVo;
import com.c88.sms.SmsProvider;
import com.c88.sms.SmsResultEnum;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component("Abenla")
@ConditionalOnProperty(prefix = "sms.otp.provider.abenla", name = "apiurl", havingValue = "http://api.abenla.com/api/SendSms")
public class AbenlaProvider implements SmsProvider {

	@Value(value = "${sms.otp.provider.abenla.apiurl}")
	private String apiurl;
	@Value(value = "${sms.otp.provider.abenla.loginName}")
	private String loginName;
	@Value(value = "${sms.otp.provider.abenla.pwd}")
	private String sendSmsPassword;
	@Value(value = "${sms.otp.provider.abenla.serviceTypeId}")
	private String serviceTypeId;
	@Value(value = "${sms.otp.provider.abenla.callBack}")
	private String callBack;

	@Override
	public SmsResultEnum sendSms(String phone, String random5digit) {
		String msg = String.format("%s", random5digit);
		SendSmsAbenlaRequestVo request = new SendSmsAbenlaRequestVo();
		request.setPhoneNumber(phone);
		request.setMessage(msg);
		request.setLoginName(loginName);
		request.setSign(GameUtil.getMD5(sendSmsPassword));
		request.setServiceTypeId(serviceTypeId);
		request.setCallBack(callBack);

		try {
			log.info("Abenla_request {} {}", apiurl, request);
			String response = HttpUtil.doGet(apiurl, "", request);
			log.info("Abenla_Response: {}", response);
			SendSmsAbenlaResponseVo resObj = new Gson().fromJson(response, SendSmsAbenlaResponseVo.class);

			switch(resObj.getCode()) {
				case "106":
					return SmsResultEnum.SUCCESS;
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