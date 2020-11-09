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

    private static final Map<String, String> URI_MAP=new HashMap<>();
    private static final Map<String, AuthToken> TOKEN_MAP=new HashMap<>();

    public static AuthToken getToken(String sessionId){
        AuthToken authToken=TOKEN_MAP.get(sessionId);
        if(authToken==null||(System.currentTimeMillis()- authToken.getCreateTime())> authToken.getExpires_in()){
            return null;
        }
        return authToken;
    }

    public static void addToken(String sessionId,AuthToken token){
        TOKEN_MAP.put(sessionId,token);
    }

    public static String getUrl(String sessionId){
        return URI_MAP.remove(sessionId);
    }

    public static void addUrl(String sessionId,String url){
        URI_MAP.put(sessionId,url);
    }

}
