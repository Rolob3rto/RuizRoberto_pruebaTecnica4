package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);
    Optional<Flight> findByFlightNumber(String flightNumber);

    Flight findFlightPricePersonById(long id);

    @Query("SELECT f FROM Flight f WHERE f.flightDate >= :dateFrom AND f.flightDate <= :dateTo AND f.origin = :origin AND f.destination = :destination")
    List<Flight> findAvailableFlights(LocalDate dateFrom, LocalDate dateTo, String origin, String destination);
}
