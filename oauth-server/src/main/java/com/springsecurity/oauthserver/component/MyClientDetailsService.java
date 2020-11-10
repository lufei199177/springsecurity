package com.springsecurity.oauthserver.component;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lufei
 * @date 2020/11/10
 * @desc
 */
public class MyClientDetailsService implements ClientDetailsService {

    private final Map<String, ClientDetails> clientDetailsStore = new HashMap();

    public MyClientDetailsService() {

    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails details = (ClientDetails) this.clientDetailsStore.get(clientId);
        if (details == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        } else {
            return details;
        }
    }

    public void addClientDetails(String clientId, ClientDetails clientDetails) {
        this.clientDetailsStore.put(clientId, clientDetails);
    }
}
