package com.springsecurity.oauthclient.controller;

import com.springsecurity.oauthclient.config.AuthConfig;
import com.springsecurity.oauthclient.model.AuthToken;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lufei
 * @date 2020/11/9
 * @desc
 */
@RestController
public class AuthController {

    @GetMapping("/authorize")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = null;
        for (Cookie c : request.getCookies()) {
            if ("user_info".equals(c.getName())) {
                cookie = c;
                break;
            }
        }
        if (cookie == null) {
            cookie = new Cookie("user_info", request.getSession().getId());
            response.addCookie(cookie);
        }
        String url = AuthConfig.authorizationUri + "?client_id=" + AuthConfig.clientId + "&redirect_uri=" + AuthConfig.redirectUri + "&response_type=code";
        response.sendRedirect(url);
    }

    @GetMapping("/login/oauth2/code/authorization")
    public void codeAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String code = request.getParameter("code");
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        parameters.add("grant_type", AuthConfig.authorizationGrantType);
        parameters.add("scope", AuthConfig.scope);
        parameters.add("redirect_uri", AuthConfig.redirectUri);

        HttpHeaders httpHeaders = new HttpHeaders();
        String cookie = request.getHeader("cookie");
        httpHeaders.add("Cookie", cookie);
        httpHeaders.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.setBasicAuth(AuthConfig.clientId, AuthConfig.clientSecret);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AuthToken> responseEntity = restTemplate.exchange(AuthConfig.tokenUri, HttpMethod.POST, httpEntity, AuthToken.class);
        AuthToken authToken = responseEntity.getBody();
        if (authToken != null) {
            authToken.setCreateTime(System.currentTimeMillis());

            String sessionId = null;
            for (Cookie c : request.getCookies()) {
                if ("user_info".equals(c.getName())) {
                    sessionId = c.getValue();
                    break;
                }
            }

            if (sessionId != null) {
                AuthConfig.addToken(sessionId, authToken);
                String url = AuthConfig.getUrl(sessionId);
                response.sendRedirect(url);
            }
        }
    }

}
