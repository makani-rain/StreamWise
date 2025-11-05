package com.price.streamwise.consumer.config;

import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtToAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final String clientId;

    public JwtToAuthConverter(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new JwtRoleConverter(clientId).convert(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }
}
