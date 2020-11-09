package com.springsecurity.oauthserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * @author lufei
 * @date 2020/10/27
 * @desc
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "authorizationServer";

    /*@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //只有/me端点作为资源服务器的资源
        http.requestMatchers().antMatchers("/me")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().csrf().disable()
        ;
    }

    /*@Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:8081/oauth/check_token");
        tokenService.setClientId("client-for-server");
        tokenService.setClientSecret("client-for-server");
        resources.tokenServices(tokenService);
    }*/
}
