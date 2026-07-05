package com.vikas.airline.dto.response;

import com.vikas.airline.enums.Role;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class UpdateUserResponse {

    Long id;

    String fullName;

    String email;

    Role role;

    boolean active;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}




