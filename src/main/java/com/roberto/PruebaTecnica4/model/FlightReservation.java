package com.roberto.PruebaTecnica4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlightReservation extends Reservation {

    @ManyToOne()
    private Flight flight;
    private String origin;
    private String destination;
    private String seatType;
}
