package com.vikas.airline.specification;

import com.vikas.airline.dto.request.TicketSearchRequest;
import com.vikas.airline.entity.Ticket;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class TicketSpecification {

    private TicketSpecification() {
    }

    public static Specification<Ticket> search(
            TicketSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("active")));

            if (request == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            if (request.getTicketNumber() != null
                    && !request.getTicketNumber().isBlank()) {

                predicates.add(cb.like(
                        cb.lower(root.get("ticketNumber")),
                        "%" + request.getTicketNumber().toLowerCase() + "%"));
            }

            if (request.getTicketStatus() != null) {

                predicates.add(cb.equal(
                        root.get("ticketStatus"),
                        request.getTicketStatus()));
            }

            if (request.getBookingId() != null) {

                predicates.add(cb.equal(
                        root.get("booking").get("id"),
                        request.getBookingId()));
            }

            if (request.getIssuedFrom() != null) {

                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("issuedAt"),
                        request.getIssuedFrom().atStartOfDay()));
            }

            if (request.getIssuedTo() != null) {

                predicates.add(cb.lessThanOrEqualTo(
                        root.get("issuedAt"),
                        request.getIssuedTo().atTime(23, 59, 59)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}