package com.vikas.airline.service;

public interface EmailService {

    void sendSimpleMail(
            String to,
            String subject,
            String body
    );

    void sendHtmlMail(
            String to,
            String subject,
            String html
    );

    void sendBookingConfirmation(
            String to,
            String passengerName,
            String bookingReference
    );

    void sendPaymentSuccess(
            String to,
            String passengerName,
            String transactionId,
            String amount
    );

    void sendTicket(
            String to,
            String passengerName,
            byte[] pdfBytes
    );

    void sendRefundSuccess(
            String to,
            String passengerName,
            String refundAmount
    );
}