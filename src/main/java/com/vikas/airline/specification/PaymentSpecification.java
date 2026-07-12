package com.vikas.airline.specification;

import com.vikas.airline.dto.request.PaymentSearchRequest;
import com.vikas.airline.entity.Payment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public final class PaymentSpecification {

    private PaymentSpecification() {
    }

    public static Specification<Payment> search(
            PaymentSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getTransactionId() != null &&
                    !request.getTransactionId().isBlank()) {

                predicates.add(
                        cb.like(
                                cb.lower(root.get("transactionId")),
                                "%" + request.getTransactionId().trim().toLowerCase() + "%"
                        )
                );
            }

            if (request.getBookingId() != null) {

                predicates.add(
                        cb.equal(
                                root.get("booking").get("id"),
                                request.getBookingId()
                        )
                );
            }

            if (request.getPaymentMethod() != null) {

                predicates.add(
                        cb.equal(
                                root.get("paymentMethod"),
                                request.getPaymentMethod()
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

            if (request.getCurrency() != null &&
                    !request.getCurrency().isBlank()) {

                predicates.add(
                        cb.equal(
                                cb.upper(root.get("currency")),
                                request.getCurrency().trim().toUpperCase()
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