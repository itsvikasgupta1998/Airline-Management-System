package com.vikas.airline.specification;

import com.vikas.airline.dto.request.FlightSearchRequest;
import com.vikas.airline.entity.Flight;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public final class FlightSpecification {

    private FlightSpecification() {
    }

    public static Specification<Flight> search(FlightSearchRequest request) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isTrue(root.get("active")));

            if (request.getSource() != null &&
                    !request.getSource().isBlank()) {

                predicates.add(

                        cb.equal(

                                cb.lower(

                                        root.join("sourceAirport")
                                                .get("city")
                                ),

                                request.getSource()
                                        .trim()
                                        .toLowerCase()
                        )
                );
            }

            if (request.getDestination() != null &&
                    !request.getDestination().isBlank()) {

                predicates.add(

                        cb.equal(

                                cb.lower(

                                        root.join("destinationAirport")
                                                .get("city")
                                ),

                                request.getDestination()
                                        .trim()
                                        .toLowerCase()
                        )
                );
            }

            if (request.getDepartureDate() != null) {
                predicates.add(cb.equal(root.get("departureDate"), request.getDepartureDate()));
            }

            if (request.getAirline() != null) {
                predicates.add(cb.equal(root.get("airline"), request.getAirline()));
            }

            if (request.getMinimumFare() != null) {
                predicates.add(cb.greaterThanOrEqualTo(
                                root.get("fare"),
                        request.getMinimumFare()));
            }

            if (request.getMaximumFare() != null) {

                predicates.add(cb.lessThanOrEqualTo(
                                root.get("fare"),
                                request.getMaximumFare()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}