package com.vikas.airline.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API Response Wrapper")
public record ApiResponse<T>(

        @Schema(example = "true")
        boolean success,

        @Schema(example = "Operation completed successfully.")
        String message,

        @Schema(description = "Response payload")
        T data,

        @Schema(example = "2026-07-05T20:45:30")
        LocalDateTime timestamp

) {


     //Success response with data.

    public static <T> ApiResponse<T> success(
            String message,
            T data
    ) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }


     //Success response without data.

    public static <T> ApiResponse<T> success(
            String message
    ) {
        return success(message, null);
    }


     //Failure response without data.

    public static <T> ApiResponse<T> failure(
            String message
    ) {
        return failure(message, null);
    }


     //Failure response with additional data(useful for validation errors).

    public static <T> ApiResponse<T> failure(
            String message,
            T data
    ) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

}