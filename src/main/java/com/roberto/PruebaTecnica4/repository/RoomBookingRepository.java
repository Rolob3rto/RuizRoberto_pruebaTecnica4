package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Long> {

    @Query("SELECT rb FROM RoomBooking rb WHERE rb.room.id = :roomId AND rb.dateFrom <= :dateTo AND rb.dateTo >= :dateFrom")
    List<RoomBooking> findOverlappingBookings(long roomId, LocalDate dateFrom, LocalDate dateTo);

    @Query("SELECT rb FROM RoomBooking rb WHERE rb.dateFrom <= :dateTo AND rb.dateTo >= :dateFrom")
    List<RoomBooking> findRoomBookingsInRange(LocalDate dateFrom, LocalDate dateTo);

}
