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
    private String userName;
    private String password;
    private GrantTypeEnum grantTypeEnum;

    public AuthClient(String clientId, String clientSecret, GrantTypeEnum grantTypeEnum) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantTypeEnum = grantTypeEnum;
    }
}
