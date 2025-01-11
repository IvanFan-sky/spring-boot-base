package com.example.common.api;

/**
 * API 返回码枚举类
 *
 * @author example
 * @version 1.0.0
 * @since 2025-01-11
 */
public enum ResultCode implements IErrorCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    
    /**
     * 操作失败
     */
    FAILED(500, "操作失败"),
    
    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(404, "参数检验失败"),
    
    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    
    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限");

    /**
     * 返回码
     */
    private int code;
    
    /**
     * 返回信息
     */
    private String message;

    /**
     * 构造函数
     *
     * @param code 返回码
     * @param message 返回信息
     */
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
