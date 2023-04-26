package com.crud.example.democrud.controller.web.user;


import com.crud.example.democrud.configs.filter.ReactiveRequestContextHolder;
import com.crud.example.democrud.controller.web.BaseController;
import com.crud.example.democrud.controller.web.BaseResponse;
import com.crud.example.democrud.controller.web.user.dto.UserRequest;
import com.crud.example.democrud.controller.web.user.dto.UserResponse;
import com.crud.example.democrud.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping
    public Mono<ResponseEntity<BaseResponse<UserResponse>>> getUser() {
        return ReactiveRequestContextHolder.getTokenAuth()
                .flatMap(userService::getName)
                .map(userName-> ResponseEntity.ok(BaseResponse.<UserResponse>builder()
                        .code(200)
                        .message("success")
                        .data(new UserResponse(userName))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<UserResponse>builder()
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(null)
                                .build()))
                );

        //TODO : handle error from service or create global error handler
    }

    @PutMapping
    public Mono<ResponseEntity<BaseResponse<UserResponse>>> getUser(@RequestBody UserRequest userRequest) {
        return ReactiveRequestContextHolder.getTokenAuth()
                .flatMap(token -> userService.updateName(token, userRequest.getUserName()))
                .map(user-> ResponseEntity.ok(BaseResponse.<UserResponse>builder()
                        .code(200)
                        .message("success")
                        .data(new UserResponse(user.getUsername()))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<UserResponse>builder()
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(null)
                                .build()))
                );

        //TODO : handle error from service or create global error handler
    }
}
