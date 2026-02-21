package com.victor.portfolio.restapi.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /**
     * Same meaning as ApiResponse.code (0 = success, non-zero = error).
     */
    private int code;

    /**
     * High-level message.
     */
    private String message;

    /**
     * Field-level validation errors, e.g. { "name": "must not be blank" }.
     */
    private Map<String, String> errors;
}
