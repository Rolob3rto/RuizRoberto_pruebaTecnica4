package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Flight;

import java.util.List;

public interface IFlightService {

    public List<Flight> getFlights();
    public void saveFlight(Flight flight);
    public void deleteFlight (Long id);
    public Flight findFlight(Long id);
    public void logicDeleteFlight(String flightNumber);
    boolean existsByFlightNumber(String flightNumber);

    public Flight findByFlightNumber(String flightNumber);

    List<Flight> getActiveFlights();
}
