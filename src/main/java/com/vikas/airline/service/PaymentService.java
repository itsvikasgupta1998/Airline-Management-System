package com.vikas.airline.service;

import com.vikas.airline.dto.request.CreatePaymentRequest;
import com.vikas.airline.dto.request.PaymentSearchRequest;
import com.vikas.airline.dto.request.RefundPaymentRequest;
import com.vikas.airline.dto.request.UpdatePaymentRequest;
import com.vikas.airline.dto.response.PaymentResponse;
import org.springframework.data.domain.Page;

public interface PaymentService {

    PaymentResponse createPayment(
            CreatePaymentRequest request);

    PaymentResponse updatePayment(
            Long id,
            UpdatePaymentRequest request);

    PaymentResponse getPaymentById(
            Long id);

    Page<PaymentResponse> getAllPayments(
            int page,
            int size,
            String sortBy,
            String sortDir);

    void deletePayment(
            Long id);

    void restorePayment(
            Long id);

    Page<PaymentResponse> searchPayments(
            PaymentSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir);

    PaymentResponse refundPayment(Long id, RefundPaymentRequest request);

    void processRefund(Long bookingId, String refundReason);

}