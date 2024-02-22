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
@DiscriminatorColumn(name = "booking_type")

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate BookingDate;
    private double totalAmount;
    private boolean active;

    @ManyToMany//(mappedBy = "bookings")
    @JoinTable(name = "person_booking",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id"))
    private List<Person> personList;

    @PrePersist
    protected void onCreate() {
        BookingDate = LocalDate.now();
    }

    public int getPersonsQuantity() {
        return personList != null ? personList.size() : 0;
    }

}
