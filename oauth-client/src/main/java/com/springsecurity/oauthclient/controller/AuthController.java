package com.springsecurity.oauthclient.controller;

import com.springsecurity.oauthclient.model.AuthClient;
import com.springsecurity.oauthclient.model.AuthToken;
import com.springsecurity.oauthclient.util.AuthUtil;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author lufei
 * @date 2020/11/9
 * @desc
 */
@RestController
public class AuthController {

    @GetMapping("/authorize")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getUserPrincipal().getName();
        AuthClient authClient = AuthUtil.AUTH_CLIENT_MAP.get(name);
        if (authClient == null) {
            try (OutputStream os = response.getOutputStream()) {
                os.write("未进行授权".getBytes("GBK"));
                os.flush();
            }
        } else {
            String url = AuthUtil.authorizationUri + "?client_id=" + authClient.getClientId() + "&redirect_uri="
                    + AuthUtil.redirectUri + "&response_type=code";
            response.sendRedirect(url);
        }
    }

    @GetMapping("/login/oauth2/code/authorization")
    public void codeAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getUserPrincipal().getName();
        AuthClient authClient = AuthUtil.AUTH_CLIENT_MAP.get(userName);

        String code = request.getParameter("code");
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        parameters.add("grant_type", AuthUtil.authorizationGrantType);
        parameters.add("scope", AuthUtil.scope);
        parameters.add("redirect_uri", AuthUtil.redirectUri);

        HttpHeaders httpHeaders = new HttpHeaders();
        String cookie = request.getHeader("cookie");
        httpHeaders.add("Cookie", cookie);
        httpHeaders.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.setBasicAuth(authClient.getClientId(), authClient.getClientSecret());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AuthToken> responseEntity = restTemplate.exchange(AuthUtil.tokenUri, HttpMethod.POST, httpEntity, AuthToken.class);
        AuthToken authToken = responseEntity.getBody();
        if (authToken != null) {
            authToken.setCreateTime(System.currentTimeMillis());

            AuthUtil.addToken(userName, authToken);
            String url = AuthUtil.getUrl(userName);
            response.sendRedirect(url);
        }
    }

    @GetMapping("/addAuthClient/{clientId}/{clientSecret}")
    public String addAuthClient(@PathVariable("clientId") String clientId, @PathVariable("clientSecret") String clientSecret, HttpServletRequest request) {
        AuthClient authClient = new AuthClient(clientId, clientSecret);
        AuthUtil.AUTH_CLIENT_MAP.put(request.getUserPrincipal().getName(), authClient);
        return "添加成功!";
    }
}
