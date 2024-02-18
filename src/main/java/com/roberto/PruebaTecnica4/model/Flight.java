package com.roberto.PruebaTecnica4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "[A-Z]{4}-[0-9]{4}", message = "El ID debe tener el formato LLLL-XXXX, donde L es una letra y X es un dígito.")
    private String flightNumber;
    private String origin;
    private String destination;
    private String typeSeat;
    private double pricePerson;
    private LocalDate oneWayDate;
    private int numSeats;
    private boolean active;

    @OneToMany()
    private List<Reservation> reservationList;

}
