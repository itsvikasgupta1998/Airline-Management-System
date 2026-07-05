package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.LoginRequest;
import com.vikas.airline.dto.request.LogoutRequest;
import com.vikas.airline.dto.request.RefreshTokenRequest;
import com.vikas.airline.dto.request.RegisterRequest;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.dto.response.AuthResponse;
import com.vikas.airline.dto.response.RefreshTokenResponse;
import com.vikas.airline.dto.response.RegisterResponse;
import com.vikas.airline.entity.User;
import com.vikas.airline.exception.EmailAlreadyExistsException;
import com.vikas.airline.exception.InvalidCredentialsException;
import com.vikas.airline.exception.UserDisabledException;
import com.vikas.airline.mapper.UserMapper;
import com.vikas.airline.repository.UserRepository;
import com.vikas.airline.security.CustomUserDetailsService;
import com.vikas.airline.security.JwtTokenProvider;
import com.vikas.airline.service.AuthService;
import com.vikas.airline.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final CustomUserDetailsService userDetailsService;


     //Registers a new user after validating email uniqueness.
    @Override
    public RegisterResponse register(RegisterRequest request) {

        String email = EmailUtils.normalize(request.getEmail());

        log.info("Registration request received for {}", email);

        if (userRepository.existsByEmail(email)) {
            log.warn("Registration failed. Email already exists : {}", email);
            throw new EmailAlreadyExistsException(email);
        }

        User user = userMapper.toEntity(request);
        user.setEmail(EmailUtils.normalize(request.getEmail()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    /**
     * Authenticates user and generates JWT tokens.
     */
    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {

        String email = EmailUtils.normalize(request.getEmail());

        log.info("Login request received for {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isActive()) {
            log.warn("Disabled account login attempt : {}", email);
            throw new UserDisabledException(email);
        }

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException ex) {

            log.warn("Invalid credentials for {}", email);

            throw new InvalidCredentialsException();
        }

        log.info("Login successful for {}", email);

        return generateTokens(user);
    }

    /**
     * Generates JWT access and refresh tokens.
     */
    private AuthResponse generateTokens(User user) {

        return AuthResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(user))
                .refreshToken(jwtTokenProvider.generateRefreshToken(user))
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    ) {

        String refreshToken = request.refreshToken;

        String email = jwtTokenProvider.extractEmail(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isActive()) {
            throw new UserDisabledException(email);
        }

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(email);

        if (!jwtTokenProvider.isTokenValid(refreshToken, userDetails)) {
            throw new InvalidCredentialsException();
        }

        return RefreshTokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(user))
                .refreshToken(jwtTokenProvider.generateRefreshToken(user))
                .build();
    }

    @Override
    @Transactional
    public void logout(LogoutRequest request) {

        String refreshToken = request.getRefreshToken();

        if (!jwtTokenProvider.isValidToken(refreshToken)
                || !jwtTokenProvider.isRefreshToken(refreshToken)) {

            throw new BadRequestException("Invalid refresh token.");
        }

        log.info("User logged out successfully.");
    }


}