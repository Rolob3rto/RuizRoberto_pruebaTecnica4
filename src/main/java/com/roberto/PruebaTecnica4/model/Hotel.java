package com.roberto.PruebaTecnica4.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private long id;

    @Pattern(regexp = "[A-Z]{2}-[0-9]{4}", message = "El ID debe tener el formato LL-XXXX, donde L es una letra y X es un dígito.")
    @Column(unique = true)
    private String hotelCode;
    @Column(nullable = false)
    private String place;
    private boolean active;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Room> roomList;

}
