package com.crud.example.democrud.service.user;

import com.crud.example.democrud.domains.user.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<String> getName(String token); //use header only JWT
    Mono<User> updateName(String token, String newName);//use header only JWT
}
