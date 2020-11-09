package com.springsecurity.oauthclient.controller;

import com.springsecurity.oauthclient.config.AuthConfig;
import com.springsecurity.oauthclient.model.AuthToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @GetMapping("/test")
    public String test(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String sessionId=request.getRequestedSessionId();
        AuthToken authToken= AuthConfig.getToken(sessionId);
        if(authToken==null){
            AuthConfig.addUrl(sessionId,"/test");
            response.sendRedirect("/authorize");
            return null;
        }
        return "test";
    }
}
