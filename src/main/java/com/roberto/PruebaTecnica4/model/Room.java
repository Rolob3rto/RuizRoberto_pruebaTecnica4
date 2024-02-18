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
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<RoomReservation> roomReservationList;
    private boolean isBooked;


    //private LocalDate sinceDate;
    //private LocalDate untilDate;


}
