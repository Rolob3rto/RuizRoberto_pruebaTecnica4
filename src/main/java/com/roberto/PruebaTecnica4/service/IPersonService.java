package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.model.Person;

import java.util.List;

public interface IPersonService {
    boolean existsByEMail(String eMail);

    Person findByEMail(String eMail);

    void savePerson(Person existingPerson);

    void deletePerson(String email);

    void logicDeletePerson(String email);

    List<Person> getActivePersons();
}
