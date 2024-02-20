package com.roberto.PruebaTecnica4.repository;

import com.roberto.PruebaTecnica4.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
