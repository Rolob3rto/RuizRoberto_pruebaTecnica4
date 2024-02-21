package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.exceptions.PersonNotFoundException;
import com.roberto.PruebaTecnica4.model.Room;
import com.roberto.PruebaTecnica4.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public void deleteRoom(String roomName) {
        Room room = findByName(roomName);
        if (room != null) {
            roomRepository.delete(room);
        }
    }

    public boolean existsByName(String roomName) {
        return roomRepository.existsByName(roomName);
    }

    public Room findByName(String roomName) {
        return roomRepository.findByName(roomName).orElseThrow(() -> new PersonNotFoundException("No se encontró ninguna habitacion con el nombre: " + roomName));
    }

    public void logicDeleteRoom(String roomName) {
        Room room = roomRepository.findByName(roomName).orElseThrow(() -> new PersonNotFoundException("No se encontró ninguna habitacion con el nombre: " + roomName));
        if (room != null) {
            room.setActive(false);
            roomRepository.save(room);
        }
    }

    public List<Room> getActiveRooms() {
        return roomRepository.findByActiveTrue();
    }
}
