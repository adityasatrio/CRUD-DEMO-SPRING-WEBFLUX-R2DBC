package com.crud.example.democrud.service.user;

import com.crud.example.democrud.configs.security.JwtValidationUtil;
import com.crud.example.democrud.domains.user.model.User;
import com.crud.example.democrud.domains.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtValidationUtil jwtValidationUtil;

    @Override
    public Mono<String> getName(String token) {
        return Mono.just(jwtValidationUtil.getPhone(token))
                .flatMap(userRepository::findByPhone)
                .filter(Objects::nonNull)
                .map(User::getUsername)
                .doOnError(throwable -> new Throwable("error when get username", throwable.getCause()));
    }
    @Override
    public Mono<User> updateName(String token, String newName) {
        return Mono.just(jwtValidationUtil.getPhone(token))
                .flatMap(userRepository::findByPhone)
                .filter(Objects::nonNull)
                .flatMap(user -> {
                    user.setUsername(newName);
                    return userRepository.save(user);
                })
                .doOnError(throwable -> new Throwable("error when update username", throwable.getCause()));
    }
}
