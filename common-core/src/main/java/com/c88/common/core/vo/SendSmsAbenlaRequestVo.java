package com.c88.common.core.vo;

import lombok.Data;

@Data
public class SendSmsAbenlaRequestVo {

	private String loginName;
	private String sign;
	private String serviceTypeId;
	private String phoneNumber;
	private String message;
	private String brandName;
	private String callBack;
	private String smsGuid;

}
