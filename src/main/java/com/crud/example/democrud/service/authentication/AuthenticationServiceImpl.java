package com.crud.example.democrud.service.authentication;

import com.crud.example.democrud.configs.security.JwtTokenUtil;
import com.crud.example.democrud.configs.security.UserPasswordEncoder;
import com.crud.example.democrud.controller.web.authentication.dto.RegisterRequest;
import com.crud.example.democrud.domains.user.model.Role;
import com.crud.example.democrud.domains.user.model.User;
import com.crud.example.democrud.domains.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final UserPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<User> register(RegisterRequest registerRequest) {
        return userRepository.findByPhone(registerRequest.getPhoneNumber())
                .filter(Objects::nonNull)
                .flatMap(user -> Mono.<User>error(new RuntimeException("Phone number already registered")))
                .switchIfEmpty(saveNewUser(registerRequest));
    }
    private Mono<User> saveNewUser(RegisterRequest registerRequest) {
        User newUser = User.builder()
                .phone(registerRequest.getPhoneNumber())
                .username(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Arrays.asList(Role.ROLE_USER))
                .enabled(Boolean.TRUE)
                .createdBy(registerRequest.getName())
                .build();

        return userRepository.save(newUser);
    }

    @Override
    public Mono<String> login(String phone, String password) {
        return userRepository.findByPhone(phone)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> jwtTokenUtil.generateToken(user))
                .switchIfEmpty(Mono.<String>error(new RuntimeException("Login failed - not found phone number or wrong password")));
    }

}
