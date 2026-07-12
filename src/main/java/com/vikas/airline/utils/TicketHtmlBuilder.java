package com.vikas.airline.utils;

import com.vikas.airline.entity.Booking;
import com.vikas.airline.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketHtmlBuilder {

    public String build(
            Ticket ticket,
            Booking booking) {

        return """
                <html>

                <body style='font-family:Arial'>

                <h1>Airline E-Ticket</h1>

                <hr/>

                <p><b>Ticket No:</b> %s</p>

                <p><b>Booking Ref:</b> %s</p>

                <p><b>Passenger:</b> %s</p>

                <p><b>Flight:</b> %s</p>

                <p><b>Source:</b> %s</p>

                <p><b>Destination:</b> %s</p>

                <p><b>Date:</b> %s</p>

                <p><b>Seat:</b> %s</p>

                <p><b>Class:</b> %s</p>

                <p><b>Total Fare:</b> ₹ %s</p>

                </body>

                </html>
                """
                .formatted(

                        ticket.getTicketNumber(),

                        booking.getBookingReference(),

                        booking.getPassenger().getFirstName()
                                + " "
                                + booking.getPassenger().getLastName(),

                        booking.getFlight().getFlightNumber(),

                        booking.getFlight()
                                .getSourceAirport()
                                .getIataCode(),

                        booking.getFlight()
                                .getDestinationAirport()
                                .getIataCode(),

                        booking.getFlight()
                                .getDepartureDate(),

                        booking.getSeat().getSeatNumber(),

                        booking.getTravelClass(),

                        booking.getTotalFare());
    }

}