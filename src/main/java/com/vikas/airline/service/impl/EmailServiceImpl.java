package com.vikas.airline.service.impl;

import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendSimpleMail(
            String to,
            String subject,
            String body) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom("vikasgupta20031998@gmail.com");
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);

        } catch (MailException ex) {

            throw new BadRequestException(
                    "Failed to send email.");
        }
    }

    @Override
    public void sendHtmlMail(
            String to,
            String subject,
            String html) {

        try {

            MimeMessage mimeMessage =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            mimeMessage,
                            true,
                            "UTF-8");

            helper.setTo(to);
            helper.setFrom("vikasgupta20031998@gmail.com");
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException | MailException ex) {

            throw new BadRequestException("Failed to send HTML email.");
        }
    }

    @Override
    public void sendBookingConfirmation(
            String to,
            String passengerName,
            String bookingReference) {

        String body = """
                Dear %s,

                Your booking has been confirmed.

                Booking Reference : %s

                Thank you for choosing our Airline.

                Happy Journey!
                """.formatted(
                passengerName,
                bookingReference);

        sendSimpleMail(
                to,
                "Booking Confirmation",
                body);
    }

    @Override
    public void sendPaymentSuccess(
            String to,
            String passengerName,
            String transactionId,
            String amount) {

        String body = """
                Dear %s,

                Payment Successful.

                Transaction Id : %s

                Amount : ₹%s

                Thank you.
                """.formatted(
                passengerName,
                transactionId,
                amount);

        sendSimpleMail(
                to,
                "Payment Successful",
                body);
    }

    @Override
    public void sendTicket(
            String to,
            String passengerName,
            byte[] pdfBytes) {

        try {

            MimeMessage mimeMessage =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);

            helper.setSubject("Your Airline E-Ticket");

            helper.setText(
                    "Dear "
                            + passengerName
                            + ",\n\nPlease find your e-ticket attached.");

            helper.addAttachment(
                    "E-Ticket.pdf",
                    new ByteArrayResource(pdfBytes));

            mailSender.send(mimeMessage);

        } catch (MessagingException | MailException ex) {

            throw new BadRequestException(
                    "Failed to send ticket.");
        }
    }

    @Override
    public void sendRefundSuccess(
            String to,
            String passengerName,
            String refundAmount) {

        String body = """
                Dear %s,

                Your refund has been processed successfully.

                Refund Amount : ₹%s

                Thank you.
                """.formatted(
                passengerName,
                refundAmount);

        sendSimpleMail(
                to,
                "Refund Processed",
                body);
    }
}