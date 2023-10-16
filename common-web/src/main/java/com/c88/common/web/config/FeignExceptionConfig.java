package com.c88.common.web.config;

import com.c88.common.core.exception.MyFeignClientException;
import com.c88.common.core.exception.MyFeignServerException;
import com.c88.common.core.result.Result;
import com.c88.common.core.result.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FeignExceptionConfig {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(MyFeignServerException.class)
	public Result handleFeignServerException(MyFeignServerException e) {
		e.printStackTrace();
		return Result.failed(ResultCode.INTERNAL_SERVICE_CALLEE_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MyFeignClientException.class)
	public Result handleFeignClientException(MyFeignClientException e) {
		e.printStackTrace();
		return Result.failed(ResultCode.INTERNAL_SERVICE_CALLER_ERROR);
	}

}
