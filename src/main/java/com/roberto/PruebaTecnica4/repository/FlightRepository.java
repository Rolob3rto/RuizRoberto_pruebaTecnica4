package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);
    Optional<Flight> findByFlightNumber(String flightNumber);

    boolean itsDeleted (String flightNumber);
}
