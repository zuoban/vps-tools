package com.zuoban.toy.vpstools.supports.advice;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.zuoban.toy.vpstools.supports.bean.RestResponse;
import com.zuoban.toy.vpstools.supports.exception.PermissionDeniedException;
import com.zuoban.toy.vpstools.supports.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author wangjinqiang
 * @date 2018-09-23
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionAdvice {
    /**
     * Bean 校验异常处理
     *
     * @param e 异常
     * @return 错误信息
     */
    @ExceptionHandler(value = {BindException.class, ConstraintViolationException.class})
    public RestResponse validateExceptionHandler(Exception e) {
        Map<String, String> errorMap = MapUtil.newHashMap();
        if (e instanceof BindException) {
            BindingResult bindingResult = ((BindException) e).getBindingResult();

            List<ObjectError> allErrors = bindingResult.getAllErrors();
            allErrors.forEach(it -> {
                FieldError fieldError = (FieldError) it;
                String key = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                errorMap.put(key, message);
            });

        } else {
            ConstraintViolationException e1 = (ConstraintViolationException) e;
            e1.getConstraintViolations().forEach(it -> {
                String message = it.getMessage();
                String key = it.getPropertyPath().toString();

                int lastPointIndex = key.lastIndexOf(".");
                if (lastPointIndex > 0) {
                    key = key.substring(lastPointIndex + 1);
                }
                errorMap.put(key, message);
            });
        }
        String message = errorMap.entrySet().stream().map(it -> it.getKey() + ": " + it.getValue()).collect(Collectors.joining("，"));
        return RestResponse.badRequest().setData(errorMap).setMessage(message);
    }


    @ExceptionHandler(value = Exception.class)
    public RestResponse exceptionHandler(Exception e) {
        String message = e.getMessage();
        if (StrUtil.isBlank(message)) {
            message = ExceptionUtil.getSimpleMessage(e);
        }

        log.error("", e);
        return RestResponse.fail().setMessage(StrUtil.blankToDefault(message, e.getMessage()));
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public RestResponse exceptionHandler(UnauthorizedException e) {
        return RestResponse.unauthorized().setMessage("请登录!");
    }

    @ExceptionHandler(value = PermissionDeniedException.class)
    public RestResponse exceptionHandler(PermissionDeniedException e) {
        return RestResponse.unauthorized().setMessage("权限不足!");
    }

}
