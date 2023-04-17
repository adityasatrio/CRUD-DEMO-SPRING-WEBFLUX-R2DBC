package com.crud.example.democrud.service.authentication;

import com.crud.example.democrud.controller.web.authentication.dto.RegisterRequest;
import com.crud.example.democrud.domains.user.model.User;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<User> register(RegisterRequest registerRequest);
    Mono<String> login(String phone, String password);
}
