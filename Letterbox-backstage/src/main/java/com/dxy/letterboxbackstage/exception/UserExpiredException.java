package com.dxy.letterboxbackstage.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: JasonD
 * @date: 2023/6/24 21:05
 * @Description:
 */
public class UserExpiredException extends AuthenticationException {
    public UserExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserExpiredException(String msg) {
        super(msg);
    }
}
