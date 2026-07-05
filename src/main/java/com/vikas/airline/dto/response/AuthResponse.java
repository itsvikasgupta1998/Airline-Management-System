package com.vikas.airline.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Authentication response")
public record AuthResponse(

        @Schema(description = "JWT Access Token")
        String accessToken,

        @Schema(description = "JWT Refresh Token")
        String refreshToken,

         UserResponse user

) {
}