package com.vikas.airline.controller;

import com.vikas.airline.dto.request.BookingSearchRequest;
import com.vikas.airline.dto.request.CancelBookingRequest;
import com.vikas.airline.dto.request.CreateBookingRequest;
import com.vikas.airline.dto.request.UpdateBookingRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.BookingResponse;
import com.vikas.airline.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {

        BookingResponse response = bookingService.createBooking(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Booking created successfully.",
                        response));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingRequest request) {

        BookingResponse response =
                bookingService.updateBooking(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booking updated successfully.",
                        response));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booking retrieved successfully.",
                        bookingService.getBookingById(id)));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookingResponse>>> getAllBookings(

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
                        "Bookings retrieved successfully.",
                        bookingService.getAllBookings(
                                page,
                                size,
                                sortBy,
                                sortDir)));
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<BookingResponse>>> searchBookings(

            BookingSearchRequest request,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir) {

        System.out.println(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Bookings retrieved successfully.",
                        bookingService.searchBookings(
                                request,
                                page,
                                size,
                                sortBy,
                                sortDir)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBooking(
            @PathVariable Long id) {

        bookingService.deleteBooking(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booking deleted successfully.",
                        null));
    }


    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreBooking(
            @PathVariable Long id) {

        bookingService.restoreBooking(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booking restored successfully.",
                        null));
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<BookingResponse>> cancelBooking(

            @PathVariable Long id,
            @Valid @RequestBody CancelBookingRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Booking cancelled successfully.",
                        bookingService.cancelBooking(id, request)));
    }

}