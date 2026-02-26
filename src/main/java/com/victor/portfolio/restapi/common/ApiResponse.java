package com.victor.portfolio.restapi.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(String message, T data) {
        return ApiResponse.<T>builder()
                .code(0)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse<Object> ok(String message) {
        return ApiResponse.builder()
                .code(0)
                .message(message)
                .data(null)
                .build();
    }

    public static ApiResponse<Object> error(String message, Object data) {
        return ApiResponse.builder()
                .code(1)
                .message(message)
                .data(data)
                .build();
    }
}