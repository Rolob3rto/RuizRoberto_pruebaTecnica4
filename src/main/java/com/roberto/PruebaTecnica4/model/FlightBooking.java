package com.roberto.PruebaTecnica4.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@DiscriminatorValue("flightBooking")
public class FlightBooking extends Booking {

    @ManyToOne()
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
