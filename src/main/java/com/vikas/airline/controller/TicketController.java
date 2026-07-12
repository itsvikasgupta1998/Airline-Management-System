package com.vikas.airline.controller;

import com.vikas.airline.dto.request.TicketSearchRequest;
import com.vikas.airline.dto.request.UpdateTicketRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.TicketResponse;
import com.vikas.airline.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicketById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket fetched successfully.",
                        ticketService.getTicketById(id)
                )
        );
    }

    @GetMapping("/number/{ticketNumber}")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicketByNumber(
            @PathVariable String ticketNumber) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket fetched successfully.",
                        ticketService.getTicketByNumber(ticketNumber)
                )
        );
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<TicketResponse>> getTicketByBookingId(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket fetched successfully.",
                        ticketService.getTicketByBookingId(bookingId)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TicketResponse>>> getAllTickets(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Tickets fetched successfully.",
                        ticketService.getAllTickets(
                                page,
                                size,
                                sortBy,
                                sortDir)
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<TicketResponse>>> searchTickets(

            TicketSearchRequest request,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Tickets fetched successfully.",
                        ticketService.searchTickets(
                                request,
                                page,
                                size,
                                sortBy,
                                sortDir)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TicketResponse>> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket updated successfully.",
                        ticketService.updateTicket(id, request)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTicket(
            @PathVariable Long id) {

        ticketService.deleteTicket(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket deleted successfully.",
                        null)
        );
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreTicket(
            @PathVariable Long id) {

        ticketService.restoreTicket(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket restored successfully.",
                        null)
        );
    }

    @PatchMapping("/booking/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelTicket(
            @PathVariable Long bookingId) {

        ticketService.cancelTicket(bookingId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket cancelled successfully.",
                        null)
        );
    }

    @PostMapping("/booking/{bookingId}/resend")
    public ResponseEntity<ApiResponse<Void>> resendTicket(
            @PathVariable Long bookingId) {

        ticketService.resendTicket(bookingId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Ticket resent successfully.",
                        null)
        );
    }

    @GetMapping("/booking/{bookingId}/download")
    public ResponseEntity<byte[]> downloadTicket(
            @PathVariable Long bookingId) {

        byte[] pdf = ticketService.downloadTicket(bookingId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=ticket-" + bookingId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length)
                .body(pdf);
    }
}