package com.roberto.PruebaTecnica4.controller;

import com.roberto.PruebaTecnica4.dto.HotelDTO;
import com.roberto.PruebaTecnica4.model.Hotel;
import com.roberto.PruebaTecnica4.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    HotelService hotelService;

    @PostMapping("/new")
    public ResponseEntity<String> createHotel(@RequestBody HotelDTO hotelDTO) {
        boolean exists = hotelService.existsByHotelCode(hotelDTO.getHotelCode());

        if (!hotelDTO.getHotelCode().matches("[A-Z]{2}-[0-9]{4}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El código del hotel proporcionado no cumple con el formato");
        }

        if (exists) {
            Hotel existingHotel = hotelService.findByHotelCode(hotelDTO.getHotelCode());

            if (existingHotel.isActive()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un hotel con ese código de hotel proporcionado");
            } else {
                existingHotel.setActive(true);
                hotelService.saveHotel(existingHotel);
                return ResponseEntity.status(HttpStatus.CREATED).body("Ya existe un hotel con ese código, se ha sido activado exitosamente, pero NO editado");
            }
        } else {
            Hotel hotel = new Hotel();
            hotel.setHotelCode(hotelDTO.getHotelCode());
            hotel.setPlace(hotelDTO.getPlace());

            hotel.setActive(true);
            hotelService.saveHotel(hotel);
            return ResponseEntity.status(HttpStatus.CREATED).body("El hotel ha sido creado exitosamente");
        }
    }

    @PutMapping("/edit/{hotelCode}")
    public ResponseEntity<String> editHotel(@PathVariable String hotelCode, @RequestBody HotelDTO hotelDTO) {
        Hotel existingHotel = hotelService.findByHotelCode(hotelCode);

        if (!hotelDTO.getHotelCode().matches("[A-Z]{2}-[0-9]{4}")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El código del hotel proporcionado no cumple con el formato");
        }

        if (existingHotel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún hotel con el código de hotel proporcionado");
        } else {
            if (!existingHotel.isActive()) {
                existingHotel.setActive(true);
            }

            existingHotel.setHotelCode(hotelDTO.getHotelCode());
            existingHotel.setPlace(hotelDTO.getPlace());

            hotelService.saveHotel(existingHotel);
            return ResponseEntity.status(HttpStatus.OK).body("El hotel ha sido editado exitosamente");
        }
    }

    @DeleteMapping("/delete/{hotelCode}")
    public ResponseEntity<String> deleteHotel(@PathVariable String hotelCode) {
        if (hotelService.existsByHotelCode(hotelCode) && hotelService.findByHotelCode(hotelCode).isActive()) {
            hotelService.logicDeleteHotel(hotelCode);
            return ResponseEntity.status(HttpStatus.OK).body("El hotel ha sido borrado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún hotel con el código: " + hotelCode);
        }
    }

    @GetMapping("/{hotelCode}")
    public ResponseEntity<?> findByHotelCode(@PathVariable String hotelCode) {
        Hotel hotel = hotelService.findByHotelCode(hotelCode);
        if (hotelService.existsByHotelCode(hotelCode) && hotel.isActive()) {
            return ResponseEntity.ok(hotel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ningún hotel con el código de hotel proporcionado");
        }
    }

    //TODO borrar si va el de abajo

    /*@GetMapping()
    public ResponseEntity<?> findAllHotels() {
        List<Hotel> hotels = hotelService.getActiveHotels();

        if (hotels.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se han encontrado hoteles activos");
        } else {
            return ResponseEntity.ok(hotels);
        }
    }*/

    @GetMapping()
    public ResponseEntity<?> getAvailableHotels(
            @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
            @RequestParam(value = "place", required = false) String place) {

        System.out.println("Fecha de inicio: " + dateFrom);
        System.out.println("Fecha de fin: " + dateTo);
        System.out.println("Lugar: " + place);

        if (dateFrom != null && dateTo != null && place != null) {
            // Lógica para filtrar hoteles según las fechas y el destino
            if (!isValidDates(dateFrom, dateTo)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Las fechas están mal proporcionadas");
            }
            List<Hotel> availableHotels = hotelService.getAvailableHotels(dateFrom, dateTo, place);

            if (availableHotels.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron hoteles disponibles para las fechas y destino especificados");
            } else {
                return ResponseEntity.ok(availableHotels);
            }

        } else {
            // Lógica para mostrar todos los hoteles
            List<Hotel> hotels = hotelService.getActiveHotels();

            if (hotels.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se han encontrado hoteles activos");
            } else {
                return ResponseEntity.ok(hotels);
            }
        }
    }
    private boolean isValidDates(LocalDate dateFrom, LocalDate dateTo) {
        return dateFrom != null && dateTo != null && !dateTo.isBefore(dateFrom);
    }
}