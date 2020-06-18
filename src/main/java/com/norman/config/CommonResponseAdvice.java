package com.norman.config;

import com.norman.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tianfei
 * @version 1.0.0
 * @description </br>
 * @date 2020/6/18 10:47 AM.
 */
@Slf4j
@RestControllerAdvice
public class CommonResponseAdvice {

    //自动处理IllegalArgumentException，包装为CommonResponse
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResponse handleException(HttpServletRequest request, IllegalArgumentException ex) {
        log.error("process url {} failed", request.getRequestURL().toString(), ex);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setSuccess(false);
        commonResponse.setCode(3001);
        commonResponse.setMessage(ex.getMessage());
        return commonResponse;
    }
}
