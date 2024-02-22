package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Person;
import com.roberto.PruebaTecnica4.model.Room;
import com.roberto.PruebaTecnica4.model.RoomBooking;
import com.roberto.PruebaTecnica4.repository.RoomBookingRepository;
import com.roberto.PruebaTecnica4.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomBookingService implements IRoomBookingService{

    @Autowired
    private RoomBookingRepository roomBookingRepository;

    @Autowired
    PersonService personService;

    @Autowired
    RoomRepository roomRepository;

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
        if (isRoomAvailable(roomBooking.getRoom(), roomBooking.getDateFrom(), roomBooking.getDateTo())) {
            roomBooking.setBookingDate(LocalDate.now());
            roomBooking.setTotalAmount(calculateTotalAmount(roomBooking));

            List<Person> persons = new ArrayList<>();
            for (Person person : roomBooking.getPersonList()) {
                // Buscar la instancia de persona por ID
                Person personSend = personService.findPersonById(person.getId());
                if (personSend != null) {
                    persons.add(personSend);
                }
            }

            // Establecer la lista de personas en la reserva de habitación
            roomBooking.setPersonList(persons);

            // Guardar la reserva de habitación en la base de datos
             roomBookingRepository.save(roomBooking);
        }
    }

    public double calculateTotalAmount(RoomBooking roomBooking) {
        Double roomPrice = roomRepository.findRoomPriceById(roomBooking.getRoom().getId());
        Integer nights = getNights(roomBooking.getDateFrom(), roomBooking.getDateTo());

        return roomPrice * nights;
    }

    public int getNights(LocalDate dateFrom, LocalDate dateTo) {
        // Calcula el número de noches entre las fechas de entrada y salida
        return (int) ChronoUnit.DAYS.between(dateFrom, dateTo);
    }

}
