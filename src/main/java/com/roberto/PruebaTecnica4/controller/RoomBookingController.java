package com.roberto.PruebaTecnica4.controller;

import com.roberto.PruebaTecnica4.dto.RoomBookingDTO;
import com.roberto.PruebaTecnica4.model.RoomBooking;
import com.roberto.PruebaTecnica4.service.RoomBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/hotel-booking")
public class RoomBookingController {

    @Autowired
    private RoomBookingService roomBookingService;

    @PostMapping("/new")
    public ResponseEntity<String> createHotelBooking(@RequestBody RoomBookingDTO requestDTO) {

        if (!isValidBookingDates(requestDTO.getDateFrom(), requestDTO.getDateTo())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No son fechas válidas");
        }

        // Comprobar disponibilidad de la habitación
        if (!roomBookingService.isRoomAvailable(requestDTO.getRoom(), requestDTO.getDateFrom(), requestDTO.getDateTo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede reservar la habitacion");
        }

        RoomBooking roomBooking = new RoomBooking();
        roomBooking.setBookingDate(LocalDate.now());
        roomBooking.setRoom(requestDTO.getRoom());
        roomBooking.setDateFrom(requestDTO.getDateFrom());
        roomBooking.setDateTo(requestDTO.getDateTo());
        roomBooking.setActive(true);

        if (requestDTO.getPersonList().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No has asignado gente a la reserva");
        }
        roomBooking.setPersonList(requestDTO.getPersonList());
        // Calcular el monto total de la reserva
        roomBooking.setTotalAmount(calculateTotalAmount(roomBooking));

        // Guardar la reserva en la base de datos
        roomBookingService.saveRoomBooking(roomBooking);

        return ResponseEntity.status(HttpStatus.OK).body("Se ha realizado la reserva con exito");
    }

        // Valida que la fecha de entrada sea anterior a la fecha de salida
    private boolean isValidBookingDates(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom != null && dateTo != null && !dateTo.isBefore(dateFrom);
    }

    private double calculateTotalAmount(RoomBooking roomBooking) {
        double amount = roomBooking.getRoom().getRoomPrice() * getNights(roomBooking.getDateFrom(), roomBooking.getDateTo());
        return amount;
    }

    public int getNights(LocalDate dateFrom, LocalDate dateTo) {
        // Calcula el número de noches entre las fechas de entrada y salida
        return (int) ChronoUnit.DAYS.between(dateFrom, dateTo) - 1;
    }
}
