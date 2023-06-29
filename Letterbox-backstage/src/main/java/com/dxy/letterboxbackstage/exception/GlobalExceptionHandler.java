package com.dxy.letterboxbackstage.exception;

import cn.hutool.jwt.JWTException;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: JasonD
 * @date: 2023/5/15 23:43
 * @Description: 全局异常捕获处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceExecption.class)
    @ResponseBody
    public Result handle(ServiceExecption se) {
        return Result.error(CodeEnum.C500, se.getMessage());
    }

    @ExceptionHandler(JWTException.class)
    @ResponseBody
    public Result handle(JWTException jwte) {
        return Result.error(CodeEnum.C403, jwte.getMessage());
    }
}
