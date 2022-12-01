package com.stars.exception;

import com.stars.common.util.ResponseData;
import com.stars.common.util.ResponseDataUtil;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * Created by macro on 2020/2/27.
 */
@ControllerAdvice// 定义全局异常
public class GlobalExceptionHandler {
    // 自定义API异常
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public ResponseData handle(ApiException e) {
        System.out.println("---自定义异常-2-");
        if (e.getErrorCode() != null) {
            System.out.println("---自定义异常-3-");
            return ResponseDataUtil.buildError(e.getErrorCode());
        }
        return ResponseDataUtil.buildError(e.getMessage());
    }
    // 校验异常信息
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseData handleValidException(MethodArgumentNotValidException e) {
        System.out.println("---全局异常-1-");
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return ResponseDataUtil.buildError("999", message);
    }
    // 校验异常信息
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public ResponseData handleValidException(BindException e) {
        System.out.println("---全局异常-2-");
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return ResponseDataUtil.buildError("998", message);
    }
}
