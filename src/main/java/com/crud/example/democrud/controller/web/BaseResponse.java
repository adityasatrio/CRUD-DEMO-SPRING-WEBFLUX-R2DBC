package com.crud.example.democrud.controller.web;

import lombok.*;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;
    private List<String> errors;
    private long serverTime;

    public long getServerTime() {
        return System.currentTimeMillis();
    }
}
