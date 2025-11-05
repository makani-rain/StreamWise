package com.price.streamwise.catalog.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestDebugFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestDebugFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.debug("[RequestDebug] URI={} AuthorizationHeader={} AuthenticationPresent={} Authentication={}",
                request.getRequestURI(),
                auth == null ? "<none>" : (auth.length() > 200 ? auth.substring(0, 200) + "..." : auth),
                authentication != null,
                authentication);

        filterChain.doFilter(request, response);
    }
}
