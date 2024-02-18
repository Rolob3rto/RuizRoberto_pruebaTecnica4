package com.roberto.PruebaTecnica4.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

// Estrategia de herencia para que haga 1 tabla de reserva solo
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

// Columna discriminadora para identificar el tipo de reserva que es
@DiscriminatorColumn(name = "reservation_type")

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate ReservationDate;
    private int peopleQuantity;

    @ManyToMany(mappedBy = "reservations")
    private List<Person> personList;

    private boolean active;
}
