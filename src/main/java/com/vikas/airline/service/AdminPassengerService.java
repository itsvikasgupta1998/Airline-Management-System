package com.vikas.airline.service;

import com.vikas.airline.dto.response.PassengerSummaryResponse;
import org.springframework.data.domain.Page;

public interface AdminPassengerService {

    Page<PassengerSummaryResponse> getPassengersByFlight(

            Long flightId,

            int page,

            int size,

            String sortBy,

            String sortDir

    );

    Object getPassengersByFlight(Long flightId);
}
