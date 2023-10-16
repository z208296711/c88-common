package com.c88.common.core.exception;

import lombok.Data;

@Data
public class MyFeignServerException extends Exception{

	private String restUrl;
	private String responseBody;

	public MyFeignServerException(String restUrl, String responseBody) {
		super(String.format("url=%s, response=%s", restUrl, responseBody));
		this.restUrl = restUrl;
		this.responseBody = responseBody;
	}

}
