package com.springsecurity.oauthclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

/**
 * @author lufei
 * @date 2020/11/10
 * @desc
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = new User("user", this.passwordEncoder.encode("123"), Collections.singleton(new SimpleGrantedAuthority("user")));
        UserDetails admin = new User("admin", this.passwordEncoder.encode("123"), Collections.singleton(new SimpleGrantedAuthority("admin")));
        return new InMemoryUserDetailsManager(user, admin);
    }
}
