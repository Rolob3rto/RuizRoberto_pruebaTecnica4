package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Room;
import com.roberto.PruebaTecnica4.model.RoomBooking;

import java.time.LocalDate;

public interface IRoomBookingService {
    void saveRoomBooking(RoomBooking roomBooking);

    boolean isRoomAvailable(Room room, LocalDate dateFrom, LocalDate dateTo);
}
