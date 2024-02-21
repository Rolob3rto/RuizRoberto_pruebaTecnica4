package com.roberto.PruebaTecnica4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
    private long id;

    @Pattern(regexp = "[A-Z]{4}-[0-9]{4}", message = "El ID debe tener el formato LLLL-XXXX, donde L es una letra y X es un dígito.")
    @Column(unique = true, nullable = false)
    private String flightNumber;
    @Column(nullable = false)
    private String origin;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private int numSeats;
    @Column(nullable = false)
    private LocalDate flightDate;

    private String typeSeat;

    @Column(nullable = false)
    @Positive
    private double pricePerson;

    private boolean active;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlightBooking> flightBookingList;

    public Flight(String flightNumber, String origin, String destination, int numSeats, LocalDate flightDate, String typeSeat, double pricePerson, boolean active, List<FlightBooking> flightBookingList) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.numSeats = numSeats;
        this.flightDate = flightDate;
        this.typeSeat = typeSeat;
        this.pricePerson = pricePerson;
        this.active = active;
        this.flightBookingList = flightBookingList;
    }

    @JsonIgnore
    public void setActive(boolean active) {
        this.active = active;
    }
}
