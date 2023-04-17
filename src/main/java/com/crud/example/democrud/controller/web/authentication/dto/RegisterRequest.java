package com.crud.example.democrud.controller.web.authentication.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterRequest extends LoginRequest{

    @Size(max = 60, message = "Name must not exceed {max} characters")
    private String name;
}
