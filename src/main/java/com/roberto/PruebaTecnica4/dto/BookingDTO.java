package com.roberto.PruebaTecnica4.dto;

import com.roberto.PruebaTecnica4.model.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BookingDTO {

    private long id;
    private LocalDate BookingDate;
    private boolean active;
    private List<Person> personList;

    @PrePersist
    protected void onCreate() {
        BookingDate = LocalDate.now();
    }

    public int getPersonsQuantity() {
        return personList != null ? personList.size() : 0;
    }

}
