package com.vikas.airline.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "baggage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Baggage extends BaseEntity{


    private int numberOfBags;

    private double totalWeight;

    private boolean specialHandlingRequired;

    private String specialNotes;

    @OneToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
}
