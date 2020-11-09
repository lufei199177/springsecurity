package com.springsecurity.demo.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;

/**
 * @author lufei
 * @date 2020-10-17 16:08
 * @desc
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*@Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> myWebAuthenticationDetailsSource;*/

    /*@Autowired
    private MyAuthenticationProvider myAuthenticationProvider;*/

    /*@Autowired
    private UserDetailsService userDetailsService;*/

    /*@Autowired
    private SpringSessionBackedSessionRegistry redisSessionRegistry;*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**", "/captcha.jpg").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().csrfTokenRepository(new LazyCsrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())).and()
                .formLogin()
                //.authenticationDetailsSource(this.myWebAuthenticationDetailsSource)
                //.loginPage("/myLogin.html").loginProcessingUrl("/auth/form").permitAll()
                //.failureHandler(new MyAuthenticationFailureHandler())
                .and()
                //.rememberMe().userDetailsService(userDetailsService).key("test")
                .sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true)
        //.sessionRegistry(redisSessionRegistry)
        ;

        //http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        *//*PasswordEncoder passwordEncoder=PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password=passwordEncoder.encode("123");
        auth.inMemoryAuthentication()
                .withUser("user").password(password).roles("USER")
                .and()
                .withUser("admin").password(password).roles("ADMIN");*//*
        auth.authenticationProvider(this.myAuthenticationProvider);
    }*/
}
