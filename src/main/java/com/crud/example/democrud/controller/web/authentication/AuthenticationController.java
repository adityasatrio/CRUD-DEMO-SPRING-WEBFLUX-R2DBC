package com.crud.example.democrud.controller.web.authentication;

import com.crud.example.democrud.controller.web.BaseController;
import com.crud.example.democrud.controller.web.BaseResponse;
import com.crud.example.democrud.controller.web.authentication.dto.LoginRequest;
import com.crud.example.democrud.controller.web.authentication.dto.LoginResponse;
import com.crud.example.democrud.controller.web.authentication.dto.RegisterRequest;
import com.crud.example.democrud.controller.web.authentication.dto.RegisterResponse;
import com.crud.example.democrud.service.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController extends BaseController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully logged user"),
            @ApiResponse(responseCode = "500", description = "failed login user"),
            @ApiResponse(responseCode = "403", description = "user or password is invalid")
    })
    public Mono<ResponseEntity<BaseResponse<LoginResponse>>> login(@RequestBody LoginRequest loginRequest) {
        //TODO : catch error from DTO validation
        return authenticationService.login(loginRequest.getPhoneNumber(), loginRequest.getPassword())
                .map(token -> ResponseEntity.ok(BaseResponse.<LoginResponse>builder()
                        .code(200)
                        .message("success")
                        .data(new LoginResponse(token))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(BaseResponse.<LoginResponse>builder()
                                .code(HttpStatus.FORBIDDEN.value())
                                .message(throwable.getMessage())
                                .data(new LoginResponse(null))
                                .build())));
    }

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully register user"),
            @ApiResponse(responseCode = "500", description = "failed register user")
    })
    public Mono<ResponseEntity<BaseResponse<RegisterResponse>>> login(@RequestBody RegisterRequest registerRequest) {
        //TODO : catch error from DTO validation
        return authenticationService.register(registerRequest)
                .map(user -> ResponseEntity.ok(BaseResponse.<RegisterResponse>builder()
                        .code(200)
                        .message("success")
                        .data(new RegisterResponse(true))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<RegisterResponse>builder()
                                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new RegisterResponse(false))
                                .build()))
                );
    }
}
