package com.springsecurity.demo.config;

import com.springsecurity.demo.model.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author lufei
 * @date 2020-10-18 23:30
 * @desc
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        String msg=e.getMessage();
        Response response=new Response();
        response.setCode(-1);
        response.setMsg(msg);

        httpServletResponse.setContentType("application/json;charset=utf-8");

        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream()) {
            outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }
}
