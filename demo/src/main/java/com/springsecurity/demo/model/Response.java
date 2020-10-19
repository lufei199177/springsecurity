package com.springsecurity.demo.model;

import lombok.Data;

/**
 * @author lufei
 * @date 2020-10-18 23:42
 * @desc
 */
@Data
public class Response {

    private String msg;
    private int code;
    private Object data;

    @Override
    public String toString(){
        return "{msg:" + this.msg + ",code:" + this.code + ",data:" + (data == null ? null : data.toString())+"}";
    }
}
