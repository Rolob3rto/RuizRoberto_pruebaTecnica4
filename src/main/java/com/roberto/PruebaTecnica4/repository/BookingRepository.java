package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
