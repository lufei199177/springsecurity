package com.springsecurity.oauthserver.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lufei
 * @date 2020-11-09 22:52
 * @desc
 */
@RestController
public class AuthController {

    @RequestMapping("/userInfo")
    public Map<String, Object> checkToken(OAuth2Authentication user, @RequestParam(required = false) String client) {
        return new HashMap<>();
    }
}
