package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.exceptions.HotelNotFoundException;
import com.roberto.PruebaTecnica4.model.Hotel;
import com.roberto.PruebaTecnica4.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRepository;

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
}
