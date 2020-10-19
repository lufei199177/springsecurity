package com.springsecurity.demo.config;

import org.springframework.security.core.AuthenticationException;

/**
 * @author lufei
 * @date 2020-10-18 23:33
 * @desc
 */
public class MyAuthenticationException extends AuthenticationException {

    public MyAuthenticationException() {
        super("验证码错误");
    }
}
