package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface IHotelService {

    List<Hotel> getHotels();
    void saveHotel(Hotel hotel);
    void deleteHotel(Long id);
    Hotel findHotel(Long id);
    void logicDeleteHotel(String hotelCode);
    boolean existsByHotelCode(String hotelCode);
    Hotel findByHotelCode(String hotelCode);

    List<Hotel> getActiveHotels();

    List<Hotel> getAvailableHotels(LocalDate dateFrom, LocalDate dateTo, String place);
}
