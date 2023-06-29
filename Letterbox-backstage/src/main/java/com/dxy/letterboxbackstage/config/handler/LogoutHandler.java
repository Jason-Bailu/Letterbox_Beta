package com.dxy.letterboxbackstage.config.handler;

import cn.hutool.json.JSONUtil;
import com.dxy.letterboxbackstage.common.Result;
import com.dxy.letterboxbackstage.common.constant.CodeEnum;
import com.dxy.letterboxbackstage.utils.UserThreadLocal;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: JasonD
 * @date: 2023/5/20 11:06
 * @Description: 自定义logout处理器
 */
@Component
public class LogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        UserThreadLocal.remove();
        outputStream.write(JSONUtil.toJsonStr(Result.success("退出成功", null)).getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
