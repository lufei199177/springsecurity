package com.springsecurity.resourceserver.component;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lufei
 * @date 2020-11-12 23:21
 * @desc
 */
public class MyUserInfoTokenServices extends UserInfoTokenServices {

    public MyUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
        super(userInfoEndpointUrl, clientId);
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        OAuth2Authentication oAuth2Authentication = super.loadAuthentication(accessToken);
        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();

        Authentication token = oAuth2Authentication.getUserAuthentication();
        Map<String, Object> map = (Map<String, Object>) token.getDetails();
        List<String> scopeList = (List<String>) map.get("scopes");
        Set<String> scopes = new HashSet<>(scopeList);
        OAuth2Request request = oAuth2Request.narrowScope(scopes);
        return new OAuth2Authentication(request, token);
    }
}
