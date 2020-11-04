package com.springsecurity.myauth.constant;

/**
 * @author lufei
 * @date 2020/11/4
 * @desc
 */
public enum AuthorizedGrantTypesEnum {

    authorization_code("authorization_code");

    AuthorizedGrantTypesEnum(String name){
        this.name=name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
