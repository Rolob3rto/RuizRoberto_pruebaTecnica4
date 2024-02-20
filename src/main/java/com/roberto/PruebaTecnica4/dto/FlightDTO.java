package com.roberto.PruebaTecnica4.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlightDTO {

    private String flightNumber;
    private String origin;
    private String destination;
    private int numSeats;
    private LocalDate flightDate;
    private String typeSeat;
    private double pricePerson;

}
