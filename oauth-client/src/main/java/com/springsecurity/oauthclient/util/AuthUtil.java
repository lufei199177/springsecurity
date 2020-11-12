package com.springsecurity.oauthclient.util;

import com.springsecurity.oauthclient.model.AuthClient;
import com.springsecurity.oauthclient.model.AuthToken;
import com.springsecurity.oauthclient.model.GrantTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lufei
 * @date 2020/11/9
 * @desc
 */
public class AuthUtil {

    public static final String authorizationUri = "http://localhost:8081/oauth/authorize";
    public static final String tokenUri = "http://localhost:8081/oauth/token";
    //public static final String clientId = "client-for-server";
    //public static final String clientSecret = "client-for-server";
    public static final String authorizationGrantType = "authorization_code";
    public static final String redirectUri = "http://localhost:8080/login/oauth2/code/authorization";
    public static final String scope = "email";
    public static final String resourceUri = "http://localhost:8082/me";

    public static final Map<String, AuthClient> AUTH_CLIENT_MAP = new HashMap<>();

    static {
        AuthClient authClient = new AuthClient("clientId001", "clientSecret001", GrantTypeEnum.AUTHORIZATION_CODE);
        AUTH_CLIENT_MAP.put("user", authClient);
    }

    private static final Map<String, String> URI_MAP = new HashMap<>();
    private static final Map<String, AuthToken> TOKEN_MAP = new HashMap<>();

    public static AuthToken getToken(String userName) {
        AuthToken authToken = TOKEN_MAP.get(userName);
        if (authToken == null || (System.currentTimeMillis() - authToken.getCreateTime()) > authToken.getExpires_in() * 1000) {
            return null;
        }
        return authToken;
    }

    public static void addToken(String userName, AuthToken token) {
        TOKEN_MAP.put(userName, token);
    }

    public static String getUrl(String userName) {
        return URI_MAP.remove(userName);
    }

    public static void addUrl(String userName, String url) {
        URI_MAP.put(userName, url);
    }

}
