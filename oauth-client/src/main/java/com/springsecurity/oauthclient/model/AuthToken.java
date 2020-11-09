package com.springsecurity.oauthclient.model;

import lombok.Data;

/**
 * @author lufei
 * @date 2020/11/9
 * @desc
 */
@Data
public class AuthToken {

    private String access_token;
    private String scope;
    private String token_type;
    private long expires_in;
    private long createTime;
}
