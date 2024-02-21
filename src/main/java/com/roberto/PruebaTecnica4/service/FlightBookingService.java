package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Flight;
import com.roberto.PruebaTecnica4.model.FlightBooking;
import com.roberto.PruebaTecnica4.repository.FlightBookingRepository;
import com.roberto.PruebaTecnica4.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FlightBookingService implements IFlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    public FlightBooking saveFlightBooking(FlightBooking flightBooking) {
        Flight flight = flightService.findFlight(flightBooking.getFlight().getId());

        if (isFlightAvailable(flight, flightBooking.getPersonsQuantity())) {

            flightBooking.setBookingDate(LocalDate.now());
            flightBooking.setTotalAmount(calculateTotalAmount(flight, flightBooking.getPersonsQuantity()));

            return flightBookingRepository.save(flightBooking);
        } else {
            return null;
        }
    }

    private double calculateTotalAmount(Flight flight, int numPersons) {
        double flightPrice = flight.getPricePerson();
        return flightPrice * numPersons;
    }

    public boolean isFlightAvailable(Flight flight, int numPersons) {
        // Obtener el vuelo de la base de datos utilizando el ID
        Flight dbFlight = flightRepository.findById(flight.getId()).orElse(null);

        if (dbFlight != null) {
            // ¿hay suficientes asientos disponibles en el vuelo?
            int availableSeats = dbFlight.getNumSeats() - numPersons;

            // Si el número de asientos disponibles es mayor o igual que cero, actualizar el número de asientos en la base de datos
            if (availableSeats >= 0) {
                dbFlight.setNumSeats(availableSeats);
                flightRepository.save(dbFlight);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }




}
