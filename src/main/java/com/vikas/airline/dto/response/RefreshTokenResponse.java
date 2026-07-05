package com.vikas.airline.dto.response;

import lombok.Builder;

@Builder
public record RefreshTokenResponse(

        String accessToken,

        String refreshToken

) {
}

