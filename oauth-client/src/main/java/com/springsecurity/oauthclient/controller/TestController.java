package com.springsecurity.oauthclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author lufei
 * @date 2020/10/27
 * @desc
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(Principal principal){
        return "hello,"+principal.getName();
    }
}
