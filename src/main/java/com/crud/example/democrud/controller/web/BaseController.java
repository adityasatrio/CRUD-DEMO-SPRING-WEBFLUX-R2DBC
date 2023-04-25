package com.crud.example.democrud.controller.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@SecurityRequirement(name = "bearerAuth")
public class BaseController {

    private static final String BEARER = "Bearer ";

    public Mono<String> getToken(ServerWebExchange ex){
        return Mono.justOrEmpty(ex.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER))
                .map(authHeaderBearer -> authHeaderBearer.substring(BEARER.length()));

    }

}
