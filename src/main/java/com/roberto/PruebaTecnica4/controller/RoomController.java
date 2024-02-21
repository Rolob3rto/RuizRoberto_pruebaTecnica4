package com.roberto.PruebaTecnica4.controller;

import com.roberto.PruebaTecnica4.dto.RoomDTO;
import com.roberto.PruebaTecnica4.model.Room;
import com.roberto.PruebaTecnica4.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody RoomDTO roomDTO) {
        boolean exists = roomService.existsByName(roomDTO.getName());

        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe una habitación con ese nombre");
        } else {
            Room room = new Room();
            room.setName(roomDTO.getName());
            room.setRoomType(roomDTO.getRoomType());
            room.setRoomPrice(roomDTO.getRoomPrice());
            room.setActive(true);
            room.setHotel(roomDTO.getHotel()); // Asignar el hotel

            roomService.saveRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body("La habitación ha sido creada exitosamente");
        }
    }

    @PutMapping("/edit/{roomName}")
    public ResponseEntity<String> editRoom(@PathVariable String roomName, @RequestBody RoomDTO roomDTO) {
        Room existingRoom = roomService.findByName(roomName);

        if (existingRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna habitación con el nombre proporcionado");
        } else {
            existingRoom.setName(roomDTO.getName());
            existingRoom.setRoomType(roomDTO.getRoomType());
            existingRoom.setRoomPrice(roomDTO.getRoomPrice());

            roomService.saveRoom(existingRoom);
            return ResponseEntity.status(HttpStatus.OK).body("La habitación ha sido editada exitosamente");
        }
    }

    @DeleteMapping("/delete/{roomName}")
    public ResponseEntity<String> deleteRoom(@PathVariable String roomName) {
        if (roomService.existsByName(roomName)) {
            roomService.logicDeleteRoom(roomName);
            return ResponseEntity.status(HttpStatus.OK).body("La habitación ha sido eliminada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna habitación con el nombre: " + roomName);
        }
    }

    @GetMapping("/{roomName}")
    public ResponseEntity<?> findByName(@PathVariable String roomName) {
        Room room = roomService.findByName(roomName);
        if (room != null) {
            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna habitación con el nombre: " + roomName);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllRooms() {
        List<Room> rooms = roomService.getActiveRooms();
        if (rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se han encontrado habitaciones");
        } else {
            return ResponseEntity.ok(rooms);
        }
    }
}
