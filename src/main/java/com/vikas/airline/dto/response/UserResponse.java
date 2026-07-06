package com.vikas.airline.dto.response;

import com.vikas.airline.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private boolean active;
}