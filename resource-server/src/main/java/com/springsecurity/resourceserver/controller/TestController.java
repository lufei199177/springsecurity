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
    public Principal me(Principal principal){
        return principal;
    }
}
