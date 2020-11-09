package com.springsecurity.oauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author lufei
 * @date 2020/10/27
 * @desc
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //client-id
                .withClient("client-for-server")
                //client-server
                .secret(this.passwordEncoder.encode("client-for-server"))
                //授权类型
                .authorizedGrantTypes("authorization_code","implicit")
                //token有效时间
                .accessTokenValiditySeconds(7200)
                //token刷新时间
                .refreshTokenValiditySeconds(72000)
                //重定向url
                .redirectUris("http://localhost:8080/login/oauth2/code/authorization")
                .additionalInformation()
                //该client可以访问的资源服务器id
                .resourceIds("authorizationServer")
                //对client进行鉴权
                .authorities("ROLE_CLIENT")
                //该client可以访问的资源范围
                .scopes("profile","email","phone","aaa")
                //自动批准的范围
                .autoApprove("profile");
    }
}
