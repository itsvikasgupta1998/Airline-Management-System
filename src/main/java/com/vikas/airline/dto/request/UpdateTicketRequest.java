package com.vikas.airline.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTicketRequest {

    @Size(
            max = 500,
            message = "Remarks cannot exceed 500 characters."
    )
    private String remarks;
}