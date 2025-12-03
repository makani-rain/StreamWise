package com.price.streamwise.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.Tracer;
import brave.Span;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseFilter {

    final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    Tracer tracer;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                Span currentSpan = tracer.currentSpan();
                String traceId = currentSpan != null ? currentSpan.context().traceIdString() : "no-trace";
                logger.debug("Adding the correlation id to the outbound headers. {}", traceId);
                exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, traceId);
                logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
            }));
        };
    }
}
