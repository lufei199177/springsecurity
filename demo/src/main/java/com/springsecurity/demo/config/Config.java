package com.springsecurity.demo.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

/**
 * @author lufei
 * @date 2020/10/19
 * @desc
 */
@Configuration
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        String password = passwordEncoder.encode("123");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(password).roles("ADMIN").build());
        manager.createUser(User.withUsername("user").password(password).roles("USER").build());
        return manager;
    }

    @Bean
    public Producer captcha() {
        Properties properties = new Properties();
        //图片宽度
        properties.setProperty("kaptcha.image.width", "150");
        //图片长度
        properties.setProperty("kaptcha.image.height", "50");
        //字符集
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        //字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        com.google.code.kaptcha.util.Config config = new com.google.code.kaptcha.util.Config(properties);
        //使用默认的图形验证码实现
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
