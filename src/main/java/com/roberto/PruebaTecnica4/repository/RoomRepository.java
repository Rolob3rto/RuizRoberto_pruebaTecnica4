package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByName(String roomName);

    Optional<Room> findByName(String roomName);

    List<Room> findByActiveTrue();

    @Query("SELECT r.roomPrice FROM Room r WHERE r.id = :roomId")
    Double findRoomPriceById( Long roomId);

}
