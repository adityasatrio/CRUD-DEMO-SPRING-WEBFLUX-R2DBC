package com.crud.example.democrud.configs.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtValidationUtil jwtValidationUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String phone = jwtValidationUtil.getPhone(token);
        return Mono.justOrEmpty(jwtValidationUtil.isTokenValid(token, phone))
                .flatMap(isValid -> AuthenticatePhoneAndToken(phone, token));

    }
    private Mono<Authentication> AuthenticatePhoneAndToken(String username, String token) {
        return Mono.just(jwtValidationUtil.getClaimsToken(token))
                .map(claims -> {
                    List<String> roleList = claims.get("roles", List.class);
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            roleList.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())
                    );
                });
    }
}
