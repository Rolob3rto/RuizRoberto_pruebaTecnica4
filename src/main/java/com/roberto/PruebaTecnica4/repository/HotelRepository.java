package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
