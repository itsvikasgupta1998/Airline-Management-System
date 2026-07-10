package com.vikas.airline.dto.response;

import com.vikas.airline.enums.SeatClass;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatResponse {

    private Long id;

    private String seatNumber;

    private SeatClass seatClass;

    private BigDecimal price;

    private Boolean booked;
}