package com.vikas.airline.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public class RefreshTokenRequest {

        @NotBlank
        public String refreshToken;

}
