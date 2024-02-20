package com.roberto.PruebaTecnica4.dto;

import com.roberto.PruebaTecnica4.model.Flight;
import com.roberto.PruebaTecnica4.model.Person;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlightBookingDTO {

    private LocalDate BookingDate;
    private boolean active;
    private List<Person> personList;
    private Flight flight;
    private int personQuantity;

}
