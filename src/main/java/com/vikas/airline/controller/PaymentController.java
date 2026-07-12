package com.vikas.airline.controller;

import com.vikas.airline.dto.request.PaymentSearchRequest;
import com.vikas.airline.dto.request.CreatePaymentRequest;
import com.vikas.airline.dto.request.RefundPaymentRequest;
import com.vikas.airline.dto.request.UpdatePaymentRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.PaymentResponse;
import com.vikas.airline.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(

            @Valid
            @RequestBody
            CreatePaymentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Payment created successfully.",
                        paymentService.createPayment(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(

            @PathVariable
            Long id,

            @Valid
            @RequestBody
            UpdatePaymentRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment updated successfully.",
                        paymentService.updatePayment(id, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(

            @PathVariable
            Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment retrieved successfully.",
                        paymentService.getPaymentById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> getAllPayments(

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
                        "Payments retrieved successfully.",
                        paymentService.getAllPayments(
                                page,
                                size,
                                sortBy,
                                sortDir)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PaymentResponse>>> searchPayments(

            PaymentSearchRequest request,

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
                        "Payments retrieved successfully.",
                        paymentService.searchPayments(
                                request,
                                page,
                                size,
                                sortBy,
                                sortDir)));
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<ApiResponse<PaymentResponse>> refundPayment(

            @PathVariable
            Long id,

            @Valid
            @RequestBody
            RefundPaymentRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment refunded successfully.",
                        paymentService.refundPayment(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePayment(

            @PathVariable
            Long id) {

        paymentService.deletePayment(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment deleted successfully.",
                        null));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restorePayment(

            @PathVariable
            Long id) {

        paymentService.restorePayment(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment restored successfully.",
                        null));
    }
}