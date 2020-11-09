package com.springsecurity.oauthserver.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lufei
 * @date 2020-11-09 22:52
 * @desc
 */
@RestController
public class AuthController {

    @RequestMapping("/userInfo")
    public Map<String, Object> checkToken(OAuth2Authentication user) {
        Map<String,Object> map=new HashMap<>();
        if(user.isAuthenticated()){
            map.put("principal",user.getPrincipal());
            map.put("name",user.getName());
            map.put("authorities",user.getAuthorities());
            map.put("details",user.getDetails());
            map.put("credentials",user.getCredentials());
            map.put("oAuth2Request",user.getOAuth2Request());
            map.put("userAuthentication",user.getUserAuthentication());
        }else{
            map.put("error","invalid token");
        }
        return map;
    }
}
