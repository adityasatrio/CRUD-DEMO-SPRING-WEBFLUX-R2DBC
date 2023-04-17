package com.crud.example.democrud.domains.user.repository;

import com.crud.example.democrud.domains.user.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByPhoneAndPassword(String phone, String password);

    Mono<User> findByPhone(String phone);
}
