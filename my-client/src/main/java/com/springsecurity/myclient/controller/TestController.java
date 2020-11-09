package com.springsecurity.myclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashMap;

/**
 * @author lufei
 * @date 2020/10/27
 * @desc
 */
@RestController
public class TestController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/hello")
    public String hello(OAuth2AuthenticationToken authentication){
        OAuth2AuthorizedClient authorizedClient=this.authorizedClientService
                .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(),authentication.getName());
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.set("Authorization","Bearer "+authorizedClient.getAccessToken().getTokenValue());
        HttpEntity<String> httpEntity=new HttpEntity<>(null,httpHeaders);
        RestTemplate restTemplate=new RestTemplate();
        String url="http://localhost:9178/me";
        ResponseEntity<String> result=restTemplate.exchange(url, HttpMethod.GET,httpEntity,String.class);
        return result.getBody();
    }
}
