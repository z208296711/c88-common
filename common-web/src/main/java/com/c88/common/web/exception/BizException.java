package com.c88.common.web.exception;


import com.c88.common.core.result.IResultCode;
import com.c88.common.core.result.ResultCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

    public IResultCode resultCode;

    private String customCode;

    /**
     * 參考：{@link ResultCode}
     *
     * @param errorCode 後端已有定義好的code
     */
    public BizException(IResultCode errorCode) {
        super(errorCode.getMsg());
        this.resultCode = errorCode;
    }

    /**
     * 參考：{@link ResultCode}
     *
     * @param errorCode 後端已有定義好的code
     * @param message   自訂錯誤訊息
     */
    public BizException(IResultCode errorCode, String message) {
        super(message);
        this.resultCode = errorCode;
    }

    /**
     * 自訂的code，對應前端定義好的 i18n 訊息
     *
     * @param customCode
     */
    public BizException(String customCode) {
        super();
        this.customCode = customCode;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

}
