package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Flight;
import com.roberto.PruebaTecnica4.model.FlightBooking;
import com.roberto.PruebaTecnica4.model.Person;
import com.roberto.PruebaTecnica4.repository.FlightBookingRepository;
import com.roberto.PruebaTecnica4.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightBookingService implements IFlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private PersonService personService;

    public FlightBooking saveFlightBooking(FlightBooking flightBooking) {
        Flight flight = flightService.findFlight(flightBooking.getFlight().getId());

        if (isFlightAvailable(flight, flightBooking.getPersonsQuantity())) {
            flightBooking.setBookingDate(LocalDate.now());
            flightBooking.setTotalAmount(calculateTotalAmount(flight, flightBooking.getPersonsQuantity()));

            List<Person> persons = new ArrayList<>();
            for (Person person : flightBooking.getPersonList()) {
                // Buscar la instancia de persona por ID
                Person personSend = personService.findPersonById(person.getId());
                System.out.println("persona para la lista: " + personSend);
                if (personSend != null) {
                    persons.add(personSend);
                }
            }
            System.out.println("lista de personas: " + persons);
            // Establecer la lista de personas en la reserva de vuelo
            flightBooking.setPersonList(persons);

            System.out.println("lista seteada:" + flightBooking.getPersonList());

            // Guardar la reserva de vuelo en la base de datos
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
