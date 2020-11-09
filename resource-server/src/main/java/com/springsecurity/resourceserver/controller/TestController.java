package com.springsecurity.resourceserver.controller;

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
    public String me(Principal principal) {
        return "访问resource成功:" + principal.getName();
    }

    @GetMapping("/test")
    public String test() {
        return "测试成功!";
    }
}
