package com.roberto.PruebaTecnica4.controller;

import com.roberto.PruebaTecnica4.dto.FlightBookingDTO;
import com.roberto.PruebaTecnica4.model.FlightBooking;
import com.roberto.PruebaTecnica4.repository.FlightRepository;
import com.roberto.PruebaTecnica4.service.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/flight-booking")
public class FlightBookingController {

    @Autowired
    private FlightBookingService flightBookingService;

    @Autowired
    private FlightRepository flightRepository;

    @PostMapping("/new")
    public ResponseEntity<String> createFlightBooking(@RequestBody FlightBookingDTO requestDTO) {


        FlightBooking flightBooking = new FlightBooking();
        flightBooking.setBookingDate(LocalDate.now());
        flightBooking.setFlight(requestDTO.getFlight());
        flightBooking.setTotalAmount(calculateTotalAmount(flightBooking));
        flightBooking.setActive(true);

        if (requestDTO.getPersonList().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No has asignado personas a la reserva");
        }
        flightBooking.setPersonList(requestDTO.getPersonList());

        // Calcular el monto total de la reserva
        flightBooking.setTotalAmount(calculateTotalAmount(flightBooking));

        // Guardar la reserva en la base de datos
        if (flightBookingService.saveFlightBooking(flightBooking) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No cabe mas gente en el vuelo");
        }


        return ResponseEntity.status(HttpStatus.OK).body("Se ha realizado la reserva con éxito");
    }

    // Válida que la fecha de salida sea posterior a la fecha de salida
    private boolean isValidBookingDates(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom != null && dateTo != null && !dateTo.isBefore(dateFrom);
    }

    // Método para calcular el monto total de la reserva de vuelo
    private double calculateTotalAmount(FlightBooking flightBooking) {
        double flightPrice = flightRepository.findFlightPricePersonById(flightBooking.getFlight().getId()).getPricePerson();
        int numPassengers = flightBooking.getPersonsQuantity();
        return flightPrice * numPassengers;
    }
}


