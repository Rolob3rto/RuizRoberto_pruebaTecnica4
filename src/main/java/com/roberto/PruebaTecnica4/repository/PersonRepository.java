package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);
    Optional<Person> findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByDni(String dni);
}
