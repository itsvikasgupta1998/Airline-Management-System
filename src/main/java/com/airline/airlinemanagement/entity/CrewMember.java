package com.airline.airlinemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crew_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role; // e.g., Pilot, Co-Pilot, Cabin Crew

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
