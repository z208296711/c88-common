package com.c88.common.core.vo;

import lombok.Data;

@Data
public class SendSmsNuageRequestVo {

	private String msg;
	private String receiver;
	private String name;
	private String pwd;
	private String checksum;

}
