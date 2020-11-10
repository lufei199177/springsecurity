package com.springsecurity.oauthserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author lufei
 * @date 2020/10/27
 * @desc
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //只有/me端点作为资源服务器的资源
        http.requestMatchers().antMatchers("/me", "/userInfo")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
        ;
    }
}
