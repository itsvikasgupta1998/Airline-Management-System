package com.vikas.airline.specification;

import com.vikas.airline.dto.request.NotificationSearchRequest;
import com.vikas.airline.entity.Notification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class NotificationSpecification {

    private NotificationSpecification() {
    }

    public static Specification<Notification> search(
            NotificationSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("active")));

            if (request == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            if (request.getBookingId() != null) {

                predicates.add(cb.equal(
                        root.get("booking").get("id"),
                        request.getBookingId()));
            }

            if (request.getUserId() != null) {

                predicates.add(cb.equal(
                        root.get("booking").get("user").get("id"),
                        request.getUserId()));
            }

            if (request.getRecipientEmail() != null
                    && !request.getRecipientEmail().isBlank()) {

                predicates.add(cb.like(
                        cb.lower(root.get("booking")
                                .get("user")
                                .get("email")),
                        "%" + request.getRecipientEmail()
                                .trim()
                                .toLowerCase() + "%"));
            }

            if (request.getSubject() != null
                    && !request.getSubject().isBlank()) {

                predicates.add(cb.like(
                        cb.lower(root.get("subject")),
                        "%" + request.getSubject()
                                .trim()
                                .toLowerCase() + "%"));
            }

            if (request.getType() != null) {

                predicates.add(cb.equal(
                        root.get("type"),
                        request.getType()));
            }

            if (request.getStatus() != null) {

                predicates.add(cb.equal(
                        root.get("status"),
                        request.getStatus()));
            }

            if (request.getSentFrom() != null) {

                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("sentAt"),
                        request.getSentFrom()));
            }

            if (request.getSentTo() != null) {

                predicates.add(cb.lessThanOrEqualTo(
                        root.get("sentAt"),
                        request.getSentTo()));
            }

            if (request.getCreatedFrom() != null) {

                predicates.add(cb.greaterThanOrEqualTo(
                        root.get("createdAt"),
                        request.getCreatedFrom()));
            }

            if (request.getCreatedTo() != null) {

                predicates.add(cb.lessThanOrEqualTo(
                        root.get("createdAt"),
                        request.getCreatedTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}