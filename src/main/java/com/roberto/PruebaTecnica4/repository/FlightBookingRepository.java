package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {


}
