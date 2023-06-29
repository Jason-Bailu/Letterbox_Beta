package com.dxy.letterboxbackstage.config.handler;

import cn.hutool.json.JSONUtil;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import com.dxy.letterboxbackstage.utils.UserThreadLocal;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: JasonD
 * @date: 2023/5/19 23:33
 * @Description: 登录失败处理器
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSONUtil.toJsonStr(Result.error(CodeEnum.C401, exception.getMessage())).getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
