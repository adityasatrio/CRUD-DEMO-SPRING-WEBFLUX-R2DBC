package com.crud.example.democrud.configs.filter;

import reactor.core.publisher.Mono;

public class ReactiveRequestContextHolder {
    private static final String BEARER = "Bearer ";
    public static Mono<String> getTokenAuth() {
        return Mono.deferContextual(contextView -> contextView.get(ContextKey.CTX_TOKEN))
                .cast(String.class)
                .filter(authHeader -> authHeader.startsWith(BEARER))
                .map(authHeaderBearer -> authHeaderBearer.substring(BEARER.length()))
                .doOnNext(s -> {
                    System.out.println("DEBUG LOG = "+s);
                });
    }
}
