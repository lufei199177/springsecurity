package com.springsecurity.oauthclient.controller;

import com.springsecurity.oauthclient.config.AuthConfig;
import com.springsecurity.oauthclient.model.AuthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author lufei
 * @date 2020/10/27
 * @desc
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(Principal principal) {
        return "hello," + principal.getName();
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = null;
        for (Cookie c : request.getCookies()) {
            if ("user_info".equals(c.getName())) {
                sessionId = c.getValue();
                break;
            }
        }
        AuthToken authToken = AuthConfig.getToken(sessionId);
        if (authToken == null) {
            AuthConfig.addUrl(sessionId, "/test");
            response.sendRedirect("/authorize");
            return null;
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + authToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String sb = AuthConfig.resourceUri;

        ResponseEntity<String> responseEntity = restTemplate.exchange(sb, HttpMethod.GET, httpEntity, String.class);

        return responseEntity.getBody();
    }
}
