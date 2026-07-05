package com.vikas.airline.entity;

import com.vikas.airline.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {



    @Column(
            name = "full_name",
            nullable = false,
            length = 100
    )
    private String fullName;

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(nullable = false)
    private boolean active = true;

}