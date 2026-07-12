package com.vikas.airline.dto.request;

import com.vikas.airline.enums.TicketStatus;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketSearchRequest {

    private String ticketNumber;
    private Long bookingId;
    private Long passengerId;
    private Long flightId;
    private TicketStatus ticketStatus;
    private LocalDate issuedFrom;
    private LocalDate issuedTo;
    private Boolean active;
}