package com.springsecurity.demo.filter;

import com.springsecurity.demo.config.MyAuthenticationException;
import com.springsecurity.demo.config.MyAuthenticationFailureHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author lufei
 * @date 2020-10-18 22:31
 * @desc
 */
public class VerificationCodeFilter extends OncePerRequestFilter {

    private final AuthenticationFailureHandler authenticationFailureHandler=new MyAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri=httpServletRequest.getRequestURI();
        //非登录请求不校验验证码
        if("/auth/form".equals(uri)){
            try{
                this.verificationCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest,httpServletResponse);
            }catch (AuthenticationException e){
                //e.printStackTrace();
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }
        }else{
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }

    }

    public void verificationCode(HttpServletRequest request) throws AuthenticationException {
        String requestCode=request.getParameter("captcha");
        HttpSession session=request.getSession();
        String saveCode= (String) session.getAttribute("captcha");
        if(!StringUtils.isEmpty(saveCode)){
            session.removeAttribute("captcha");
        }
        if(StringUtils.isEmpty(requestCode)||StringUtils.isEmpty(saveCode)||!requestCode.equals(saveCode)){
            throw new MyAuthenticationException();
        }
    }
}
