package com.springsecurity.oauthserver.controller;

import com.springsecurity.oauthserver.component.MyClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author lufei
 * @date 2020-11-09 22:52
 * @desc
 */
@RestController
public class AuthController {

    @Autowired
    private MyClientDetailsService myClientDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/userInfo")
    public Map<String, Object> checkToken(OAuth2Authentication user) {
        Map<String, Object> map = new HashMap<>();
        if (user.isAuthenticated()) {
            map.put("principal", user.getPrincipal());
            map.put("name", user.getName());
            map.put("authorities", user.getAuthorities());
            map.put("details", user.getDetails());
            map.put("credentials", user.getCredentials());
            map.put("oAuth2Request", user.getOAuth2Request());
            map.put("userAuthentication", user.getUserAuthentication());
            map.put("scopes", user.getOAuth2Request().getScope());
        } else {
            map.put("error", "invalid token");
        }
        return map;
    }

    @GetMapping("/addClientDetail/{clientId}/{clientSecret}")
    public String addClientDetail(@PathVariable("clientId") String clientId, @PathVariable("clientSecret") String clientSecret) {
        String[] scopes = {"profile", "email", "phone", "aaa"};
        List<String> grantTypes = Arrays.asList("authorization_code", "password", "client_credentials");
        Set<String> set = new HashSet<>();
        set.add("http://localhost:8080/login/oauth2/code/authorization");

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret(this.passwordEncoder.encode(clientSecret));
        clientDetails.setRegisteredRedirectUri(set);
        clientDetails.setScope(Arrays.asList(scopes));
        clientDetails.setAuthorizedGrantTypes(grantTypes);

        this.myClientDetailsService.addClientDetails(clientId, clientDetails);
        return "添加成功！";
    }

}
