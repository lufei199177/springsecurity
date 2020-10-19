package com.springsecurity.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lufei
 * @date 2020-10-17 15:58
 * @desc
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "hello,world!";
    }
}
