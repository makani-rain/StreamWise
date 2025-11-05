package com.price.streamwise.catalog.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Converts Keycloak JWT claims into Spring Security GrantedAuthority collection.
 * It reads client roles from resource_access.{client}.roles and realm roles from realm_access.roles.
 */
public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String clientId;

    public JwtRoleConverter(String clientId) {
        this.clientId = clientId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Set<String> roles = new HashSet<>();

        Object resourceAccess = jwt.getClaims().get("resource_access");
        if (resourceAccess instanceof Map) {
            Map<String, Object> ra = (Map<String, Object>) resourceAccess;
            Object client = ra.get(clientId);
            if (client instanceof Map) {
                Object r = ((Map<String, Object>) client).get("roles");
                if (r instanceof Iterable) {
                    for (Object o : (Iterable<?>) r) {
                        roles.add(String.valueOf(o));
                    }
                }
            }
        }

        Object realmAccess = jwt.getClaims().get("realm_access");
        if (realmAccess instanceof Map) {
            Object rr = ((Map<String, Object>) realmAccess).get("roles");
            if (rr instanceof Iterable) {
                for (Object o : (Iterable<?>) rr) {
                    roles.add(String.valueOf(o));
                }
            }
        }

        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.toUpperCase()))
                .collect(Collectors.toSet());
    }
}
