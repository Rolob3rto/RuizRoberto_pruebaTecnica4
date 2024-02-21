package com.roberto.PruebaTecnica4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Room room;

    private LocalDate dateFrom;
    private LocalDate dateTo;

}
