package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}