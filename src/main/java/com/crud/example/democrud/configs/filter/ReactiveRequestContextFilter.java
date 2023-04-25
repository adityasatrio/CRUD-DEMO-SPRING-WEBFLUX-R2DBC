package com.crud.example.democrud.configs.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveRequestContextFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .contextWrite(context -> {
                    ServerHttpRequest scopedRequest = exchange.getRequest();

                    if(Objects.nonNull(scopedRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))) {
                        context.put(ContextKey.CTX_TOKEN, scopedRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
                    }

                    return context;
                });
    }
}
