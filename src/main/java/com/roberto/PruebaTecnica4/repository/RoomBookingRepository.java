package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {

}
