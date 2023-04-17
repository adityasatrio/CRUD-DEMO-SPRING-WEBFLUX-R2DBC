package com.crud.example.democrud.controller.web.user;


import com.crud.example.democrud.controller.web.BaseController;
import com.crud.example.democrud.controller.web.BaseResponse;
import com.crud.example.democrud.controller.web.user.dto.UserRequest;
import com.crud.example.democrud.controller.web.user.dto.UserResponse;
import com.crud.example.democrud.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController(value = "user")
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping
    public Mono<ResponseEntity<BaseResponse<UserResponse>>> getUser(ServerWebExchange exchange) {
        return getToken(exchange)
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
