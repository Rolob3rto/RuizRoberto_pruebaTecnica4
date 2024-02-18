package com.roberto.PruebaTecnica4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = "[A-Z]{2}-[0-9]{4}", message = "El ID debe tener el formato LL-XXXX, donde L es una letra y X es un dígito.")
    private String hotelCode;
    private String place;
    private boolean active;

    @OneToMany
    private List<Room> roomList;

}
