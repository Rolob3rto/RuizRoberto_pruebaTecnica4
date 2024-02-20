package com.roberto.PruebaTecnica4.dto;

import com.roberto.PruebaTecnica4.model.Room;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HotelDTO {

    private String hotelCode;
    private String place;
    private List<Room> roomList;

}
