package com.airline.airlinemanagement.controller;

import com.airline.airlinemanagement.dto.response.AuthResponse;
import com.airline.airlinemanagement.dto.request.LoginRequest;
import com.airline.airlinemanagement.dto.request.RegisterRequest;
import com.airline.airlinemanagement.entity.User;
import com.airline.airlinemanagement.repository.UserRepository;
import com.airline.airlinemanagement.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }

        User user = User.builder()
                .email(req.getEmail())
                .fullName(req.getFullName())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .active(true).build();

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        String accessToken = tokenProvider.generateAccessToken(req.getEmail());
        String refreshToken = tokenProvider.generateRefreshToken(req.getEmail());

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }
}
