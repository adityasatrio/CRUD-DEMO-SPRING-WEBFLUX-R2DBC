package com.crud.example.democrud.configs.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    private static final String LOGIN_URL = "/login";
    private static final String REGISTER_URL = "/register";
    private static final String SWAGGER_URL = "/webjars/swagger-ui/**";

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(
                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)) //failed authenticate 401
                )
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(
                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)) //no authenticate 403
                )
                .and()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST).permitAll()
                .pathMatchers(
                        LOGIN_URL,
                        REGISTER_URL,
                        SWAGGER_URL
                )
                .permitAll()
                .anyExchange().authenticated()
                .and()
                .build();
    }
}
