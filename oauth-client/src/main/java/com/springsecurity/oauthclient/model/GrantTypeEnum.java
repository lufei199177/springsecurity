package com.springsecurity.oauthclient.model;

/**
 * @author lufei
 * @date 2020/11/12
 * @desc
 */
public enum GrantTypeEnum {

    AUTHORIZATION_CODE("code"), IMPLICIT("token"), PASSWORD("password"),
    CLIENT_CREDENTIALS("client_credentials");

    GrantTypeEnum(String code) {
        this.code = code;
    }

    private final String code;

    public String getCode() {
        return code;
    }
}
