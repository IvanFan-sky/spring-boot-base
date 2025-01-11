package com.example.common.exception;

import com.example.common.api.IErrorCode;

/**
 * 自定义API异常
 *
 * @author example
 * @version 1.0.0
 * @since 2025-01-11
 */
public class ApiException extends RuntimeException {
    /**
     * 错误码
     */
    private IErrorCode errorCode;

    /**
     * 构造函数
     *
     * @param errorCode 错误码
     */
    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public ApiException(String message) {
        super(message);
    }

    /**
     * 构造函数
     *
     * @param cause 异常原因
     */
    public ApiException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     *
     * @param message 错误消息
     * @param cause 异常原因
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
