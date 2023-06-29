package com.dxy.letterboxbackstage.config;

import com.dxy.letterboxbackstage.config.filter.JwtAuthenticationEntryPoint;
import com.dxy.letterboxbackstage.config.filter.JwtAuthenticationFilter;
import com.dxy.letterboxbackstage.config.handler.LoginFailureHandler;
import com.dxy.letterboxbackstage.config.handler.LoginSuccessHandler;
import com.dxy.letterboxbackstage.config.handler.LogoutHandler;
import com.dxy.letterboxbackstage.service.impl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @Author: JasonD
 * @date: 2023/5/19 23:16
 * @Description:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)//用于开启security相关注解
public class SecurityConfig {
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

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //编码器
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authentication -> {return authentication;});
        return jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //开启从库中获取用户信息的配置
        httpSecurity.userDetailsService(userDetailsService);

        //开启跨域 以及csrf攻击关闭（csrf伪造攻击）
        httpSecurity
            .cors()
        .and()
            .csrf().disable()

        //自定义登录登出配置
        .formLogin()
            .successHandler(loginSuccessHandler)
            .failureHandler(loginFailureHandler)
        .and()
            .logout().logoutSuccessHandler(logoutHandler)

        //session禁止配置
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        //拦截配置
        .and()
            .authorizeRequests()
            .antMatchers(URL_WHITELIST).permitAll() //白名单访问
            .anyRequest().authenticated() //其他需要认证

        //自定义异常处理配置
        .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)

        //自定义配置
        .and()
            .addFilter(jwtAuthenticationFilter()); //添加jwt认证过滤器

        return httpSecurity.build();
    }
}
