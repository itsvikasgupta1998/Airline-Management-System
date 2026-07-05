package com.vikas.airline.dto.response;

import com.vikas.airline.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private boolean active;
    private LocalDateTime createdAt;
}
