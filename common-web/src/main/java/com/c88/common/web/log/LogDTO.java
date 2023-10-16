package com.c88.common.web.log;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogDTO implements Serializable{

	private Boolean success;
	private OperationEnum type;
	private String menu;
	private String menuPage;
	private String operationName;
	private LocalDateTime operationDate;
	private String operator;

	@JSONField(jsonDirect=true)
	private String content;
	private String beforeJson;
	private String afterJson;
	private String i18nKey;

	private String operatorLoginIp;

//	private HttpServletRequest servletRequest;

}
