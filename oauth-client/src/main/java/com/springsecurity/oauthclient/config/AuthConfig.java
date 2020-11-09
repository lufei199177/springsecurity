package com.springsecurity.oauthclient.config;

import com.springsecurity.oauthclient.model.AuthToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lufei
 * @date 2020/11/9
 * @desc
 */
public class AuthConfig {

    public static final String authorizationUri = "http://localhost:8081/oauth/authorize";
    public static final String tokenUri = "http://localhost:8081/oauth/token";
    public static final String clientId = "client-for-server";
    public static final String clientSecret = "client-for-server";
    public static final String authorizationGrantType = "authorization_code";
    public static final String redirectUri = "http://localhost:8080/login/oauth2/code/authorization";
    public static final String scope = "email";
    public static final String resourceUri = "http://localhost:8082/me";

    private static final Map<String, String> URI_MAP = new HashMap<>();
    private static final Map<String, AuthToken> TOKEN_MAP = new HashMap<>();

    public static AuthToken getToken(String sessionId) {
        AuthToken authToken = TOKEN_MAP.get(sessionId);
        if (authToken == null || (System.currentTimeMillis() - authToken.getCreateTime()) > authToken.getExpires_in() * 1000) {
            return null;
        }
        return authToken;
    }

    public static void addToken(String sessionId, AuthToken token) {
        TOKEN_MAP.put(sessionId, token);
    }

    public static String getUrl(String sessionId) {
        return URI_MAP.remove(sessionId);
    }

    public static void addUrl(String sessionId, String url) {
        URI_MAP.put(sessionId, url);
    }

}
