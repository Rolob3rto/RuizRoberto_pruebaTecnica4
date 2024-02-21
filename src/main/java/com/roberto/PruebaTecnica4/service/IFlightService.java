package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Flight;

import java.util.List;

public interface IFlightService {

    List<Flight> getFlights();
    void saveFlight(Flight flight);
    void deleteFlight (Long id);
    Flight findFlight(Long id);
    void logicDeleteFlight(String flightNumber);
    boolean existsByFlightNumber(String flightNumber);

    Flight findByFlightNumber(String flightNumber);

    List<Flight> getActiveFlights();
}
