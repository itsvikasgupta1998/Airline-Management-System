package com.vikas.airline.dto.response;

import com.vikas.airline.enums.Role;

import java.time.LocalDateTime;

public class UserResponse {

        Long id;

        String fullName;

        String email;

        Role role;

        boolean active;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;


}

