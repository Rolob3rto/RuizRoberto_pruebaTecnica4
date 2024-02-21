package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.exceptions.HotelNotFoundException;
import com.roberto.PruebaTecnica4.model.Hotel;
import com.roberto.PruebaTecnica4.model.Room;
import com.roberto.PruebaTecnica4.model.RoomBooking;
import com.roberto.PruebaTecnica4.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomBookingService roomBookingService;

    @Override
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public void saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public void logicDeleteHotel(String hotelCode) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode)
                .orElseThrow(() -> new HotelNotFoundException("No se encontró ningún hotel con el código: " + hotelCode));

        hotel.setActive(false);

        hotelRepository.save(hotel);
    }

    @Override
    public boolean existsByHotelCode(String hotelCode) {
        return hotelRepository.existsByHotelCode(hotelCode);
    }

    @Override
    public Hotel findByHotelCode(String hotelCode) {
        return hotelRepository.findByHotelCode(hotelCode)
                .orElseThrow(() -> new HotelNotFoundException("No se encontró ningún hotel con el código: " + hotelCode));
    }

    @Override
    public Hotel findHotel(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("No se encontró ningún hotel con el id: " + id));
    }

    @Override
    public List<Hotel> getActiveHotels() {
        List<Hotel> allHotels = hotelRepository.findAll();
        return allHotels.stream()
                .filter(Hotel::isActive) // Filtrar solo hoteles activos
                .collect(Collectors.toList());
    }

    @Override
    public List<Hotel> getAvailableHotels(LocalDate dateFrom, LocalDate dateTo, String place) {
        System.out.println("Fecha de inicio recibida en el servicio: " + dateFrom);
        System.out.println("Fecha de fin recibida en el servicio: " + dateTo);
        System.out.println("Lugar recibido en el servicio: " + place);
        // Obtener todas las reservas que se solapan con las fechas especificadas
        List<RoomBooking> bookingsInRange = roomBookingService.findRoomBookingsInRange(dateFrom, dateTo);

        // Identificar las habitaciones asociadas a las reservas solapadas
        List<Long> bookedRoomIds = bookingsInRange.stream()
                .map(booking -> booking.getRoom().getId())
                .collect(Collectors.toList());

        // Filtrar los hoteles disponibles
        List<Hotel> availableHotels = hotelRepository.findHotelsByPlace(place).stream()
                .filter(hotel -> hotel.getRoomList().stream()
                        .noneMatch(room -> roomIsBooked(room, bookedRoomIds, dateFrom, dateTo)))
                .collect(Collectors.toList());

        return availableHotels;
    }

    // verificando si una habitación está reservada para las fechas dadas
    private boolean roomIsBooked(Room room, List<Long> bookedRoomIds, LocalDate dateFrom, LocalDate dateTo) {
        return bookedRoomIds.contains(room.getId()) &&
                room.getRoomBookingList().stream()
                        .anyMatch(booking -> isOverlapping(booking.getDateFrom(), booking.getDateTo(), dateFrom, dateTo));
    }

    // verificando si las fechas se solapan
    private boolean isOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !end1.isBefore(start2) && !start1.isAfter(end2);
    }
}
