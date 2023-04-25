package com.crud.example.democrud.controller.web.user;


import com.crud.example.democrud.configs.filter.ReactiveRequestContextHolder;
import com.crud.example.democrud.controller.web.BaseController;
import com.crud.example.democrud.controller.web.BaseResponse;
import com.crud.example.democrud.controller.web.user.dto.UserRequest;
import com.crud.example.democrud.controller.web.user.dto.UserResponse;
import com.crud.example.democrud.service.user.UserService;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping
    public Mono<ResponseEntity<BaseResponse<UserResponse>>> getUser(@RequestHeader(name = "authorization") String authorization) {
        /*return getToken(exchange)
                .flatMap(userService::getName)
                .map(userName-> ResponseEntity.ok(BaseResponse.<UserResponse>builder()
                        .code(200)
                        .message("success")
                        .data(new UserResponse(userName))
                        .build()));*/

        return ReactiveRequestContextHolder.getTokenAuth()
                .flatMap(userService::getName)
                .map(userName-> ResponseEntity.ok(BaseResponse.<UserResponse>builder()
                        .code(200)
                        .message("success")
                        .data(new UserResponse(userName))
                        .build()));

        //TODO : handle error from service or create global error handler
    }

    @PutMapping
    public Mono<ResponseEntity<BaseResponse<UserResponse>>> getUser(ServerWebExchange exchange,
                                                                    @RequestBody UserRequest userRequest) {
        return getToken(exchange)
                .flatMap(token -> userService.updateName(token, userRequest.getUserName()))
                .map(user-> ResponseEntity.ok(BaseResponse.<UserResponse>builder()
                        .code(200)
                        .message("success")
                        .data(new UserResponse(user.getUsername()))
                        .build()));

        //TODO : handle error from service or create global error handler
    }
}
