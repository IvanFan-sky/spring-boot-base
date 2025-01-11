package com.example.common.exception;

import com.example.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 用于统一处理系统中抛出的各类异常
 *
 * @author example
 * @version 1.0.0
 * @since 2025-01-11
 */
@Slf4j  // Lombok注解，自动创建日志对象
@RestControllerAdvice  // 标记这是一个全局异常处理器，处理所有Controller中抛出的异常
public class GlobalExceptionHandler {

    /**
     * 处理自定义API异常
     *
     * @param e API异常
     * @return 处理结果
     */
    @ExceptionHandler(ApiException.class)  // 指定要处理的异常类型
    public Result<?> handleApiException(ApiException e) {
        // 记录异常日志
        log.error("Api异常：{}", e.getMessage());
        // 返回错误信息
        return Result.failed(e.getMessage());
    }

    /**
     * 处理参数验证异常
     * 当使用@Valid注解验证请求参数时，如果验证失败会抛出此异常
     *
     * @param e 参数验证异常
     * @return 处理结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e) {
        // 获取绑定结果
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        // 如果有错误
        if (bindingResult.hasErrors()) {
            // 获取第一个字段错误
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                // 组装错误信息：字段名 + 错误信息
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        // 返回错误信息
        return Result.failed(message);
    }

    /**
     * 处理参数绑定异常
     * 处理form data方式调用接口时的参数错误
     *
     * @param e 参数绑定异常
     * @return 处理结果
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        // 获取绑定结果
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        // 如果有错误
        if (bindingResult.hasErrors()) {
            // 获取第一个字段错误
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                // 组装错误信息：字段名 + 错误信息
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        // 返回错误信息
        return Result.failed(message);
    }

    /**
     * 处理其他所有未知异常
     * 这是最后的异常处理防线，确保所有异常都能被处理
     *
     * @param e 未知异常
     * @return 处理结果
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        // 记录异常堆栈信息
        log.error("系统异常：", e);
        // 返回通用错误信息，避免将具体错误暴露给用户
        return Result.failed("系统异常，请联系管理员");
    }
}
