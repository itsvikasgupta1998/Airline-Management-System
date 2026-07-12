package com.vikas.airline.service;

import com.vikas.airline.dto.request.TicketSearchRequest;
import com.vikas.airline.dto.request.UpdateTicketRequest;
import com.vikas.airline.dto.response.TicketResponse;
import org.springframework.data.domain.Page;

public interface TicketService {

    TicketResponse getTicketById(Long id);

    TicketResponse getTicketByNumber(String ticketNumber);

    TicketResponse getTicketByBookingId(Long bookingId);

    Page<TicketResponse> getAllTickets(
            int page,
            int size,
            String sortBy,
            String sortDir);

    Page<TicketResponse> searchTickets(
            TicketSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir);

    TicketResponse updateTicket(Long id, UpdateTicketRequest request);

    void deleteTicket(Long id);

    void restoreTicket(Long id);

    void generateTicket(Long bookingId);

    void cancelTicket(Long bookingId);

    byte[] downloadTicket(Long bookingId);

    void resendTicket(Long bookingId);

}