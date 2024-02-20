package com.roberto.PruebaTecnica4.dto;

import com.roberto.PruebaTecnica4.model.Person;
import com.roberto.PruebaTecnica4.model.Room;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomBookingDTO {

    private LocalDate BookingDate;
    private List<Person> personList;
    private Room room;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int nights;
    private int personQuantity;

}
