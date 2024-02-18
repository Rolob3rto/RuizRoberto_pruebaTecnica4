package com.roberto.PruebaTecnica4.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String roomType;
    private double roomPrice;
    private boolean active;

    @ManyToOne
    private Hotel hotel;

    @OneToMany
    private List<RoomReservation> reservationList;
    private boolean isBooked;


    //private LocalDate sinceDate;
    //private LocalDate untilDate;


}
