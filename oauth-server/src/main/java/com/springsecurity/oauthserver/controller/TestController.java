package com.springsecurity.oauthserver.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author lufei
 * @date 2020/10/29
 * @desc
 */
@RestController
public class TestController {

    @GetMapping("/")
    public String home(){
        return "这是主页";
    }

    @GetMapping("/admin")
    public String main(){
        return "这是admin页";
    }

    @GetMapping("/user")
    public String user(){
        return "这是user页";
    }
}
