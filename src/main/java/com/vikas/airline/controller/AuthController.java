package com.vikas.airline.controller;

import com.vikas.airline.dto.request.LoginRequest;
import com.vikas.airline.dto.request.LogoutRequest;
import com.vikas.airline.dto.request.RefreshTokenRequest;
import com.vikas.airline.dto.request.RegisterRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.AuthResponse;
import com.vikas.airline.dto.response.RefreshTokenResponse;
import com.vikas.airline.dto.response.RegisterResponse;
import com.vikas.airline.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully.",response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login successful.",
                        authService.login(request)
                )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(
            @Valid
            @RequestBody
            RefreshTokenRequest request)
    {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Access token refreshed successfully.",
                        authService.refreshToken(request)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @Valid @RequestBody LogoutRequest request
    ) {

        authService.logout(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Logged out successfully.",
                        null
                )
        );
    }



}