package com.springsecurity.oauthserver.component;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

/**
 * @author lufei
 * @date 2020/11/10
 * @desc
 */
@Component
public class BeanComponent {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public MyClientDetailsService myClientDetailsService() {
        return new MyClientDetailsService();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("sign123");
        //jwtAccessTokenConverter.setVerifierKey("verify123");
        return jwtAccessTokenConverter;
    }
}
