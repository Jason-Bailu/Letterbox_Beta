package com.dxy.letterboxbackstage.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: JasonD
 * @date: 2023/5/20 09:47
 * @Description:
 */
public class UserLockedException extends AuthenticationException {
    public UserLockedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserLockedException(String msg) {
        super(msg);
    }
}
