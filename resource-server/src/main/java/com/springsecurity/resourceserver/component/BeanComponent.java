package com.springsecurity.resourceserver.component;

import org.springframework.context.annotation.Bean;
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
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("sign123");
        return jwtAccessTokenConverter;
    }
}
