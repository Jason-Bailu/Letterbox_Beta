package com.dxy.letterboxbackstage.config.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.dxy.letterboxbackstage.common.CheckResult;
import com.dxy.letterboxbackstage.common.constant.JwtConstant;
import com.dxy.letterboxbackstage.entity.User;
import com.dxy.letterboxbackstage.exception.UserExpiredException;
import com.dxy.letterboxbackstage.exception.UserLockedException;
import com.dxy.letterboxbackstage.service.impl.MyUserDetailsServiceImpl;
import com.dxy.letterboxbackstage.service.impl.UserServiceImpl;
import com.dxy.letterboxbackstage.utils.JwtUtils;
import com.dxy.letterboxbackstage.utils.SessionUtils;
import com.dxy.letterboxbackstage.utils.UserThreadLocal;
import com.dxy.letterboxbackstage.vo.UserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author: JasonD
 * @date: 2023/5/20 10:30
 * @Description: jwt认证过滤器
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    SessionUtils sessionUtils;

    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/file/download/**",
            "/user/register",
            "/swagger-ui/**",
            "/webjars/**",
            "/doc.html",
            "/swagger-resources/**",
            "/v3/**"
    };

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, JwtException {
        //获取header中的token
        String token = request.getHeader("Authorization");
        System.out.println("请求的URL：" + request.getRequestURI());

        //如果token为空，或者请求的地址为白名单地址直接放行
        if (StrUtil.isEmpty(token) || new ArrayList<String>(Arrays.asList(URL_WHITELIST)).contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        //如果token不为空，则需要验证
        CheckResult checkResult = JwtUtils.validateJWT(token);
        if (!checkResult.isSuccess()) {
            switch (checkResult.getErrCode()) {
                case JwtConstant.JWT_ERRCODE_NULL:
                    throw new JwtException("token不存在");
                case JwtConstant.JWT_ERRCODE_EXPIRE:
                    throw new JwtException("token已过期");
                case JwtConstant.JWT_ERRCODE_FAIL:
                    throw new JwtException("token验证不通过");
            }
        }

        Claims claims = JwtUtils.parseJWT(token);
        String username = claims.getSubject();
        if (!sessionUtils.getKey(username)) {
            // 如果有无该用户session记录就抛出异常
            throw new UserExpiredException("用户已登出，请重新登录");
        } else {
            // 如果有该用户session记录就发送心跳进行session更新
            sessionUtils.sendHeartbeat(username);
        }

        User user = userService.getByUsername(username);
        //将登录后的用户信息保存到threadlocal
        UserVO userVo = BeanUtil.copyProperties(user, UserVO.class);
        UserThreadLocal.set(userVo);
        //设置token认证和授权
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetailsService.getUserAuthority(user.getId()));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }
}
