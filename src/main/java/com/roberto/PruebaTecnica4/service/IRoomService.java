package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Room;

import java.util.List;

public interface IRoomService{
    List<Room> getAllRooms();

    void saveRoom(Room room);

    void deleteRoom(String roomName);

    boolean existsByName(String roomName);

    Room findByName(String roomName);

    void logicDeleteRoom(String roomName);

    List<Room> getActiveRooms();
}
