package com.roberto.PruebaTecnica4.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@DiscriminatorValue("roomBooking")
public class RoomBooking extends Booking {

    @ManyToOne()
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int nights;

}
