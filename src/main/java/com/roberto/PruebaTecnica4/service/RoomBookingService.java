package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Room;
import com.roberto.PruebaTecnica4.model.RoomBooking;
import com.roberto.PruebaTecnica4.repository.RoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomBookingService implements IRoomBookingService{

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    // Devuelve true si la habitación está disponible, de lo contrario, devuelve false
    public boolean isRoomAvailable(Room room, LocalDate dateFrom, LocalDate dateTo) {
        // Consultar en el repositorio de reservas si hay reservas existentes para la habitación y fechas proporcionadas
        List<RoomBooking> overlappingBookings = roomBookingRepository.findOverlappingBookings(room.getId(), dateFrom, dateTo);

        // Verificar si hay reservas que se superpongan con las fechas de la nueva reserva
        for (RoomBooking booking : overlappingBookings) {
            if (dateFrom.isBefore(booking.getDateTo()) && dateTo.isAfter(booking.getDateFrom())) {
                // Existe una reserva que se superpone
                return false;
            }
        }

        // No hay reservas que se superpongan
        return true;
    }

    @Override
    public List<RoomBooking> findRoomBookingsInRange(LocalDate dateFrom, LocalDate dateTo) {
        return roomBookingRepository.findRoomBookingsInRange(dateFrom, dateTo);
    }

    public void saveRoomBooking(RoomBooking roomBooking) {

        if (isRoomAvailable(roomBooking.getRoom(), roomBooking.getDateFrom(), roomBooking.getDateTo())){
            roomBookingRepository.save(roomBooking);
        }
    }
}
