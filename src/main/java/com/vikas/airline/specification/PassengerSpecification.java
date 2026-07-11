package com.vikas.airline.specification;

import com.vikas.airline.dto.request.PassengerSearchRequest;
import com.vikas.airline.entity.Passenger;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;


public class PassengerSpecification {

    public static Specification<Passenger> search(
            PassengerSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getFirstName() != null &&
                    !request.getFirstName().isBlank()) {

                predicates.add(

                        cb.like(
                                cb.lower(root.get("firstName")),
                                "%" + request.getFirstName().toLowerCase() + "%"
                        )

                );

            }

            if (request.getLastName() != null &&
                    !request.getLastName().isBlank()) {

                predicates.add(

                        cb.like(
                                cb.lower(root.get("lastName")),
                                "%" + request.getLastName().toLowerCase() + "%"
                        )

                );

            }

            if (request.getPassportNumber() != null &&
                    !request.getPassportNumber().isBlank()) {

                predicates.add(

                        cb.equal(
                                cb.lower(root.get("passportNumber")),
                                request.getPassportNumber().toLowerCase()
                        )

                );

            }

            if (request.getEmail() != null &&
                    !request.getEmail().isBlank()) {

                predicates.add(

                        cb.equal(
                                cb.lower(root.get("email")),
                                request.getEmail().toLowerCase()
                        )

                );

            }

            if (request.getNationality() != null &&
                    !request.getNationality().isBlank()) {

                predicates.add(

                        cb.equal(
                                cb.lower(root.get("nationality")),
                                request.getNationality().toLowerCase()
                        )

                );

            }

            if (request.getGender() != null) {

                predicates.add(

                        cb.equal(
                                root.get("gender"),
                                request.getGender()
                        )

                );

            }

            predicates.add(
                    cb.isTrue(root.get("active"))
            );

            return cb.and(predicates.toArray(new Predicate[0]));

        };
    }

}

