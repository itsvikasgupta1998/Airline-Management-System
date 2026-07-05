package com.vikas.airline.dto.response;

import com.vikas.airline.enums.Role;
import lombok.Builder;

@Builder
public class UserSummaryResponse {

        Long id;
        String fullName;
        String email;
        Role role;
        boolean active;

}
