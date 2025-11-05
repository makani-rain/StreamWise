package com.price.streamwise.consumer.config;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class OAuth2ClientCredentialsInterceptor implements ClientHttpRequestInterceptor {

    private final OAuth2AuthorizedClientManager manager;
    private final String clientRegistrationId;
    private final Logger logger = LoggerFactory.getLogger(OAuth2ClientCredentialsInterceptor.class);

    public OAuth2ClientCredentialsInterceptor(OAuth2AuthorizedClientManager manager, String clientRegistrationId) {
        this.manager = manager;
        this.clientRegistrationId = clientRegistrationId;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        // try to forward incoming authorization header
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest incoming = attrs.getRequest();
                String incomingAuth = incoming.getHeader("Authorization");
                if (incomingAuth != null && !incomingAuth.isBlank()) {
                    logger.debug("Forwarding incoming Authorization header to outgoing request (clientRegistrationId={})", clientRegistrationId);
                    request.getHeaders().set("Authorization", incomingAuth);
                    return execution.execute(request, body);
                }
            }
        } catch (Exception ex) {
            logger.debug("Unable to read incoming Authorization header: {}", ex.toString());
        }

        // if it's not there, obtain a client_credentials token and attach it
        Authentication principal = new AnonymousAuthenticationToken("key", "consumer-service",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

        OAuth2AuthorizeRequest authRequest = OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
                .principal(principal)
                .build();

        OAuth2AuthorizedClient client = manager.authorize(authRequest);
        if (client != null && client.getAccessToken() != null) {
            logger.debug("Attaching client_credentials access token for clientRegistrationId={}", clientRegistrationId);
            request.getHeaders().setBearerAuth(client.getAccessToken().getTokenValue());
        }

        return execution.execute(request, body);
    }
}
