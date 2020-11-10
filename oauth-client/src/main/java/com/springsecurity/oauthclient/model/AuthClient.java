package com.springsecurity.oauthclient.model;

import lombok.Data;

/**
 * @author lufei
 * @date 2020/11/10
 * @desc
 */
@Data
public class AuthClient {

    private String clientId;
    private String clientSecret;

    public AuthClient(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
