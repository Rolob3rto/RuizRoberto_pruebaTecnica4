package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.exceptions.FlightHasReservationsException;
import com.roberto.PruebaTecnica4.exceptions.FlightNotFoundException;
import com.roberto.PruebaTecnica4.model.Flight;
import com.roberto.PruebaTecnica4.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService implements IFlightService{


    @Autowired
    FlightRepository flightRepository;

    @Override
    public List<Flight> getFlights() {

        return flightRepository.findAll();
    }

    @Override
    public void saveFlight(Flight flight) {
        flightRepository.save(flight);
    }



    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
    @Override
    public void logicDeleteFlight(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException("No se encontró ningún vuelo con el numero: " + flightNumber));

        if (flight.getFlightBookingList().isEmpty()){

            flight.setActive(false);
        } else {
            throw new FlightHasReservationsException("No se puede borrar el vuelo porque tiene reservas");
        }


        flightRepository.save(flight);

    }

    @Override
    public boolean existsByFlightNumber(String flightNumber) {
        return flightRepository.existsByFlightNumber(flightNumber);
    }

    @Override
    public Flight findByFlightNumber(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber).orElseThrow(() -> new FlightNotFoundException("No se encontró ningún vuelo con el numero: " + flightNumber));
    }

    @Override
    public Flight findFlight(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("No se encontró ningún vuelo con el id: " + id));
    }

    @Override
    public List<Flight> getActiveFlights() {
        List<Flight> allFlights = flightRepository.findAll();
        return allFlights.stream()
                .filter(Flight::isActive)
                .collect(Collectors.toList());
    }

    public List<Flight> getAvailableFlights(LocalDate dateFrom, LocalDate dateTo, String origin, String destination) {
        // Primero, verifica si las fechas son válidas
        if (!isValidDates(dateFrom, dateTo)) {
            throw new IllegalArgumentException("Las fechas proporcionadas no son válidas");
        }

        List<Flight> availableFlights = flightRepository.findAvailableFlights(dateFrom, dateTo, origin, destination);

        // Filtra los vuelos por vuelso que tengan asientos
        availableFlights = availableFlights.stream()
                .filter(flight -> flight.getNumSeats() > 0)
                .collect(Collectors.toList());

        return availableFlights;
    }

    private boolean isValidDates(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom != null && dateTo != null && !dateTo.isBefore(dateFrom);
    }


}
