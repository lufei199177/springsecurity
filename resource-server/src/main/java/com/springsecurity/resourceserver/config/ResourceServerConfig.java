package com.springsecurity.resourceserver.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author lufei
 * @date 2020/10/27
 * @desc
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "authorizationServer";

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //只有/me端点作为资源服务器的资源
        http.requestMatchers().antMatchers("/me")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
        ;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        String userInfoEndpointUrl = "http://localhost:8081/userInfo";
        //String clientId = null;
        UserInfoTokenServices tokenService = new UserInfoTokenServices(userInfoEndpointUrl, null);
        resources.tokenServices(tokenService);
    }
}
