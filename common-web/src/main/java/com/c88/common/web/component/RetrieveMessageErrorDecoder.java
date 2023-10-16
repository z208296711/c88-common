package com.c88.common.web.component;

import com.c88.common.core.result.Result;
import com.c88.common.core.result.ResultCode;
import com.c88.common.web.exception.BizException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.io.InputStream;

public class RetrieveMessageErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        Result message;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, Result.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        switch (response.status()) {
            case 400:
                if (message.getMsg() == null) {
                    return new BizException(message.getCode());
                }
                return new BizException(ResultCode.getValue(message.getCode()), message.getMsg());
            case 404:
                return new NotFoundException(message.getMsg() != null ? message.getMsg() : "Not found");
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }

}
