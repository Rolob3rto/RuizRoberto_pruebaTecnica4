package com.roberto.PruebaTecnica4.controller;

import com.roberto.PruebaTecnica4.dto.FlightDTO;
import com.roberto.PruebaTecnica4.model.Flight;
import com.roberto.PruebaTecnica4.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @PostMapping("/new")
    public ResponseEntity<String> createFlight (@RequestBody FlightDTO flightDTO){

        boolean exists = flightService.existsByFlightNumber(flightDTO.getFlightNumber());

        if (!flightDTO.getFlightNumber().matches("[A-Z]{4}-[0-9]{4}")) {
            // El comprueba el numero de vuelo
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El número de vuelo proporcionado no cumple con el formato");
        }
        // Se comprueba si ya existe en la base de datos
        if (exists) {
            Flight existingFlight = flightService.findByFlightNumber(flightDTO.getFlightNumber());

            // Se comprueba si está activo
            if (existingFlight.isActive()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un vuelo con ese número de vuelo proporcionado");
            } else {
                // Se activa el vuelo que estaba eliminado
                existingFlight.setActive(true);
                flightService.saveFlight(existingFlight);
                return ResponseEntity.status(HttpStatus.CREATED).body("Ya existe un vuelo con ese número, se ha sido activado exitosamente, pero NO editado");
            }
        } else {
            // El vuelo no existe, se crea uno nuevo
            Flight flight = new Flight();
            flight.setFlightNumber(flightDTO.getFlightNumber());
            flight.setOrigin(flightDTO.getOrigin());
            flight.setDestination(flightDTO.getDestination());
            flight.setNumSeats(flightDTO.getNumSeats());
            flight.setFlightDate(flightDTO.getFlightDate());
            flight.setTypeSeat(flightDTO.getTypeSeat());
            flight.setPricePerson(flightDTO.getPricePerson());

            flight.setActive(true);
            flightService.saveFlight(flight);
            return ResponseEntity.status(HttpStatus.CREATED).body("El vuelo ha sido creado exitosamente");
        }

    }

    @PutMapping("/edit/{flightNumber}")
    public ResponseEntity<String> editFlight (@PathVariable String flightNumber, @RequestBody FlightDTO flightDTO){
        Flight existingFlight = flightService.findByFlightNumber(flightNumber);

        if (!flightDTO.getFlightNumber().matches("[A-Z]{4}-[0-9]{4}")) {
            // El comprueba el numero de vuelo
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El número de vuelo proporcionado no cumple con el formato");
        }

        if (existingFlight == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún vuelo con el número de vuelo proporcionado");
        } else {
            if (!existingFlight.isActive()) {
                // El vuelo estaba eliminado, se activa
                existingFlight.setActive(true);
            }

            existingFlight.setFlightNumber(flightDTO.getFlightNumber());
            existingFlight.setOrigin(flightDTO.getOrigin());
            existingFlight.setDestination(flightDTO.getDestination());
            existingFlight.setNumSeats(flightDTO.getNumSeats());
            existingFlight.setFlightDate(flightDTO.getFlightDate());
            existingFlight.setTypeSeat(flightDTO.getTypeSeat());
            existingFlight.setPricePerson(flightDTO.getPricePerson());

            flightService.saveFlight(existingFlight);
            return ResponseEntity.status(HttpStatus.OK).body("El vuelo ha sido editado exitosamente");
        }
    }

    //TODO AÑADIR QUE SI TIENE RESERVAS EL VUELO NO SE PUEDA BORRAR

    @DeleteMapping("/delete/{flightNumber}")
    public ResponseEntity<String> deleteFlight (@PathVariable String flightNumber){

        if (flightService.existsByFlightNumber(flightNumber) && flightService.findByFlightNumber(flightNumber).isActive()) {
            flightService.logicDeleteFlight(flightNumber);

            return ResponseEntity.status(HttpStatus.OK).body("El vuelo ha sido borrado exitosamente");
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún vuelo con el numero: " + flightNumber);
        }

    }

    @GetMapping("/{flightNumber}")
    public ResponseEntity<?> findByFlightNumber(@PathVariable String flightNumber){
            Flight flight = flightService.findByFlightNumber(flightNumber);
        if (flightService.existsByFlightNumber(flightNumber) && flight.isActive()) {
            return ResponseEntity.ok(flight);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún vuelo con el número de vuelo proporcionado");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAvailableFlights(
            @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "destination", required = false) String destination) {

        if (dateFrom != null && dateTo != null && origin != null && destination != null) {
            // Lógica para filtrar vuelos según las fechas, origen y destino
            if (!isValidDates(dateFrom, dateTo)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las fechas están mal proporcionadas");
            }
            List<Flight> availableFlights = flightService.getAvailableFlights(dateFrom, dateTo, origin, destination);

            if (availableFlights.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron vuelos disponibles para las fechas, origen y destino especificados");
            } else {
                return ResponseEntity.ok(availableFlights);
            }

        } else {
            // Lógica para mostrar todos los vuelos
            List<Flight> flights = flightService.getActiveFlights();

            if (flights.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se han encontrado vuelos");
            } else {
                return ResponseEntity.ok(flights);
            }
        }
    }

    private boolean isValidDates(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom != null && dateTo != null && !dateTo.isBefore(dateFrom);
    }

}
