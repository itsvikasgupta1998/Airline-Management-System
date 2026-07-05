package com.vikas.airline.service;

import com.vikas.airline.dto.request.LoginRequest;
import com.vikas.airline.dto.request.LogoutRequest;
import com.vikas.airline.dto.request.RefreshTokenRequest;
import com.vikas.airline.dto.request.RegisterRequest;
import com.vikas.airline.dto.response.AuthResponse;
import com.vikas.airline.dto.response.RefreshTokenResponse;
import com.vikas.airline.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(LogoutRequest request);

}