package com.roberto.PruebaTecnica4.dto;

import com.roberto.PruebaTecnica4.model.Hotel;
import com.roberto.PruebaTecnica4.model.RoomBooking;
import lombok.*;

import java.util.List;


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
    private List<RoomBooking> roomBookingList;
    private boolean isBooked;

}
