package com.springsecurity.oauthclient.controller;

import com.springsecurity.oauthclient.model.AuthClient;
import com.springsecurity.oauthclient.model.AuthToken;
import com.springsecurity.oauthclient.model.GrantTypeEnum;
import com.springsecurity.oauthclient.util.AuthUtil;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

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
            if (authClient.getGrantTypeEnum().name().equals(GrantTypeEnum.AUTHORIZATION_CODE.name())) {
                String url = AuthUtil.authorizationUri + "?client_id=" + authClient.getClientId()
                        + "&redirect_uri=" + AuthUtil.redirectUri + "&response_type=" + GrantTypeEnum.AUTHORIZATION_CODE.getCode();
                response.sendRedirect(url);
            } else if (authClient.getGrantTypeEnum().name().equals(GrantTypeEnum.IMPLICIT.name())) {
                try (OutputStream os = response.getOutputStream()) {
                    os.write("服务器不支持IMPLICIT".getBytes("GBK"));
                    os.flush();
                }
            } else if (authClient.getGrantTypeEnum().name().equals(GrantTypeEnum.PASSWORD.name())) {
                MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
                parameters.add("client_id", authClient.getClientId());
                parameters.add("client_secret", authClient.getClientSecret());
                parameters.add("grant_type", GrantTypeEnum.PASSWORD.getCode());
                parameters.add("username", authClient.getUserName());
                parameters.add("password", authClient.getPassword());

                HttpHeaders httpHeaders = this.createHttpHeaders(authClient);

                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, httpHeaders);

                this.handleToken(httpEntity, name, response);
            } else if (authClient.getGrantTypeEnum().name().equals(GrantTypeEnum.CLIENT_CREDENTIALS.name())) {
                MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
                parameters.add("client_id", authClient.getClientId());
                parameters.add("client_secret", authClient.getClientSecret());
                parameters.add("grant_type", GrantTypeEnum.CLIENT_CREDENTIALS.getCode());

                HttpHeaders httpHeaders = this.createHttpHeaders(authClient);

                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, httpHeaders);

                this.handleToken(httpEntity, name, response);
            }
        }
    }

    @GetMapping("/login/oauth2/code/authorization")
    public void codeAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //printHttpServletRequest(request);
        String userName = request.getUserPrincipal().getName();
        AuthClient authClient = AuthUtil.AUTH_CLIENT_MAP.get(userName);

        String code = request.getParameter("code");
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("code", code);
        parameters.add("grant_type", AuthUtil.authorizationGrantType);
        parameters.add("scope", AuthUtil.scope);
        parameters.add("redirect_uri", AuthUtil.redirectUri);

        HttpHeaders httpHeaders = this.createHttpHeaders(authClient);
        String cookie = request.getHeader("cookie");
        httpHeaders.add("Cookie", cookie);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, httpHeaders);

        this.handleToken(httpEntity, userName, response);
    }

    private HttpHeaders createHttpHeaders(AuthClient authClient) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.setBasicAuth(authClient.getClientId(), authClient.getClientSecret());
        return httpHeaders;
    }

    private void handleToken(HttpEntity httpEntity, String userName, HttpServletResponse response) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AuthToken> responseEntity = restTemplate.exchange(AuthUtil.tokenUri, HttpMethod.POST, httpEntity,
                AuthToken.class);
        AuthToken authToken = responseEntity.getBody();
        if (authToken != null) {
            authToken.setCreateTime(System.currentTimeMillis());

            AuthUtil.addToken(userName, authToken);
            String url = AuthUtil.getUrl(userName);
            response.sendRedirect(url);
        }
    }

    private void printHttpServletRequest(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            System.out.println(key + ":" + request.getHeader(key));
        }
        System.out.println();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String key = paramNames.nextElement();
            System.out.println(key + ":" + request.getParameter(key));
        }

    }

    @GetMapping("/addAuthClient/{clientId}/{clientSecret}/{grantType}")
    public String addAuthClient(@PathVariable("clientId") String clientId,
                                @PathVariable("clientSecret") String clientSecret,
                                @PathVariable("grantType") GrantTypeEnum grantType,
                                HttpServletRequest request) {
        AuthClient authClient = new AuthClient(clientId, clientSecret, grantType);
        if (grantType.name().equals(GrantTypeEnum.PASSWORD.name())) {
            String userName = request.getParameter("userName");
            if (StringUtils.isEmpty(userName)) {
                return "userName不能为空";
            }
            String password = request.getParameter("password");
            if (StringUtils.isEmpty(password)) {
                return "password不能为空";
            }
            authClient.setUserName(userName);
            authClient.setPassword(password);
        }
        AuthUtil.AUTH_CLIENT_MAP.put(request.getUserPrincipal().getName(), authClient);
        return "添加成功!";
    }
}
