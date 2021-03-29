package com.chinafight.gongxiangdaoyou.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: ak
 * @date: 2020/4/1 0:16
 */
@Slf4j
@ControllerAdvice
public class CustomHandler {

    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    public Object exceptionHandler(NullPointerException e) {
        log.error("发生空指针异常！原因是:", e);
        return e;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(Exception e) {
        log.error("未知异常！原因是:", e);
        if (e.getMessage() != null) {
            return e.getMessage();
        } else if (e.getCause() != null) {
            return e.getCause();
        } else {
            return "未知异常！";
        }
    }

    /**
     * 校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validationBodyException(MethodArgumentNotValidException exception) {

        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p -> {
                FieldError fieldError = (FieldError) p;
                log.error("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() +
                        "},errorMessage{" + fieldError.getDefaultMessage() + "}");
            });
        }
        return "请填写正确信息";
    }

    /**
     * 参数类型转换错误
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageConversionException.class)
    public Object parameterTypeException(HttpMessageConversionException exception) {

        log.error(exception.getCause().getLocalizedMessage());
        return "类型转换错误";

    }
}
