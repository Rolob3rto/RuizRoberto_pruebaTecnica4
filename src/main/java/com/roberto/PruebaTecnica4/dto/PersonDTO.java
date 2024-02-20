package com.roberto.PruebaTecnica4.dto;

import com.roberto.PruebaTecnica4.model.Booking;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonDTO {

    private String name;
    private String lastName;
    private String dni;
    private String eMail;
    private List<Booking> bookings;

}
