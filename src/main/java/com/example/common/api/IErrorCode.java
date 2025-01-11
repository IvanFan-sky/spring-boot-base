package com.example.common.api;

/**
 * API 错误码接口
 *
 * @author example
 * @version 1.0.0
 * @since 2025-01-11
 */
public interface IErrorCode {
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}
