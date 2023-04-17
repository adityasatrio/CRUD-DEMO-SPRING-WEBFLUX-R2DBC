package com.crud.example.democrud.controller.web.authentication.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {

    //TODO : catch error from DTO validation
    @Pattern(regexp = "^08\\d{8,10}$", message = "Phone number is invalid")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9]).{6,16}$", message = "Password must contain at least 1 capital letter and 1 number, and be between 6 and 16 characters long")
    private String password;
}
