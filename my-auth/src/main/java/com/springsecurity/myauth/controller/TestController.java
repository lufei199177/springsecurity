package com.springsecurity.myauth.controller;

import org.springframework.context.annotation.Scope;
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

    @GetMapping("/me")
    public String me(){
        return "hello.me!";
    }

    @GetMapping("/admin")
    public String admin(){
        return "hello.admin!";
    }
}
