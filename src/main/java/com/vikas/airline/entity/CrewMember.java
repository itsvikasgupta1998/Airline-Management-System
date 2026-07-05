package com.vikas.airline.entity;

import com.vikas.airline.enums.CrewRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "crew_members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrewMember extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CrewRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "flight_id",
            nullable = false
    )
    private Flight flight;

}