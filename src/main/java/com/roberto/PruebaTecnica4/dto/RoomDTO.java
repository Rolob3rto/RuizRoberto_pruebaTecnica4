package com.roberto.PruebaTecnica4.dto;

import com.roberto.PruebaTecnica4.model.Hotel;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomDTO {

    private String name;
    private String roomType;
    private double roomPrice;
    private Hotel hotel;

}
