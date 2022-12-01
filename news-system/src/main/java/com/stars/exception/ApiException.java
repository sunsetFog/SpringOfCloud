package com.stars.exception;

import com.stars.common.interfaces.IErrorCode;

/**
 * 自定义API异常
 * 调用：ApiException.fail("该优惠券不可用");
 * Created by macro on 2020/2/27.
 */
public class ApiException extends RuntimeException {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
        System.out.println("---异常-1-");
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
