package com.springsecurity.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lufei
 * @date 2020-10-18 15:20
 * @desc
 */
@RestController
@RequestMapping("/admin/api")
public class AdminController {

    @GetMapping("/hello")
    public String hello() {
        return "hello,admin";
    }
}
