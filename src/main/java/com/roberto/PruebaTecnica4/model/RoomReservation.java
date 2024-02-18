package com.roberto.PruebaTecnica4.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomReservation extends Reservation {

    @ManyToOne()
    private Room room;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int nights;

    //private String roomType;
}
