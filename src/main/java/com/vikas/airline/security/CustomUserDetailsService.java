package com.vikas.airline.security;

import com.vikas.airline.entity.User;
import com.vikas.airline.exception.UserDisabledException;
import com.vikas.airline.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);

        log.debug("Loading user details for {}", normalizedEmail);

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> {
                    log.warn("User not found : {}", normalizedEmail);
                    return new UsernameNotFoundException("Invalid email or password.");
                });

        if (!user.isActive()) {
            log.warn("Disabled account login attempt : {}", normalizedEmail);
            throw new UserDisabledException(email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name().replace("ROLE_", ""))
                .disabled(!user.isActive())
                .build();
    }
}