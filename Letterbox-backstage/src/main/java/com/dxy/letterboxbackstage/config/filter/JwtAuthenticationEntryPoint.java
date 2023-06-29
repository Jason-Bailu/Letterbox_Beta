package com.dxy.letterboxbackstage.config.filter;

import cn.hutool.json.JSONUtil;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: JasonD
 * @date: 2023/5/20 11:01
 * @Description: jwt认证异常处理器
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSONUtil.toJsonStr(Result.error(CodeEnum.C401, authException.getMessage())).getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
