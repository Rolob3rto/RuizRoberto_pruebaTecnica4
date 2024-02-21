package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.exceptions.PersonNotFoundException;
import com.roberto.PruebaTecnica4.model.Room;
import com.roberto.PruebaTecnica4.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements IRoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void deleteRoom(String roomName) {
        Room room = findByName(roomName);
        if (room != null) {
            roomRepository.delete(room);
        }
    }

    @Override
    public boolean existsByName(String roomName) {
        return roomRepository.existsByName(roomName);
    }

    @Override
    public Room findByName(String roomName) {
        return roomRepository.findByName(roomName).orElseThrow(() -> new PersonNotFoundException("No se encontró ninguna habitacion con el nombre: " + roomName));
    }

    @Override
    public void logicDeleteRoom(String roomName) {
        Room room = roomRepository.findByName(roomName).orElseThrow(() -> new PersonNotFoundException("No se encontró ninguna habitacion con el nombre: " + roomName));
        if (room != null) {
            room.setActive(false);
            roomRepository.save(room);
        }
    }

    @Override
    public List<Room> getActiveRooms() {
        return roomRepository.findByActiveTrue();
    }
}
