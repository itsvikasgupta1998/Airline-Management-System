package com.vikas.airline.dto.response;

import com.vikas.airline.enums.TicketStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {

    private Long id;

    private String ticketNumber;

    private TicketStatus ticketStatus;

    private LocalDateTime issuedAt;

    private LocalDateTime cancelledAt;

    private String qrCode;

    private String remarks;

    private Boolean active;

    private Long bookingId;

    private String bookingReference;

    private Long passengerId;

    private String passengerName;

    private Long flightId;

    private String flightNumber;

    private String sourceAirport;

    private String destinationAirport;
}