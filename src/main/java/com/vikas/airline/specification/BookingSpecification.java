package com.vikas.airline.specification;

import com.vikas.airline.dto.request.BookingSearchRequest;
import com.vikas.airline.entity.Booking;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class BookingSpecification {

    private BookingSpecification() {
    }

    public static Specification<Booking> search(BookingSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getBookingReference() != null &&
                    !request.getBookingReference().isBlank()) {

                predicates.add(
                        cb.like(
                                cb.lower(root.get("bookingReference")),
                                "%" + request.getBookingReference().trim().toLowerCase() + "%"
                        )
                );
            }

            if (request.getFlightId() != null) {

                predicates.add(
                        cb.equal(
                                root.get("flight").get("id"),
                                request.getFlightId()
                        )
                );
            }

            if (request.getPassengerId() != null) {

                predicates.add(
                        cb.equal(
                                root.get("passenger").get("id"),
                                request.getPassengerId()
                        )
                );
            }

            if (request.getUserId() != null) {

                predicates.add(
                        cb.equal(
                                root.get("user").get("id"),
                                request.getUserId()
                        )
                );
            }

            if (request.getBookingStatus() != null) {

                predicates.add(
                        cb.equal(
                                root.get("bookingStatus"),
                                request.getBookingStatus()
                        )
                );
            }

            if (request.getPaymentStatus() != null) {

                predicates.add(
                        cb.equal(
                                root.get("paymentStatus"),
                                request.getPaymentStatus()
                        )
                );
            }

            if (request.getActive() != null) {

                predicates.add(
                        cb.equal(
                                root.get("active"),
                                request.getActive()
                        )
                );

            } else {

                predicates.add(
                        cb.isTrue(root.get("active"))
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}