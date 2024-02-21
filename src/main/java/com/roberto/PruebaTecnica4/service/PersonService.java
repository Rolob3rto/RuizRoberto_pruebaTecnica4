package com.roberto.PruebaTecnica4.service;

import com.roberto.PruebaTecnica4.exceptions.PersonNotFoundException;
import com.roberto.PruebaTecnica4.model.Person;
import com.roberto.PruebaTecnica4.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService implements IPersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public boolean existsByEMail(String email) {
        return personRepository.existsByEmail(email);
    }

    @Override
    public Person findByEMail(String email) {
        return personRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException("No se encontró ninguna persona con el email: " + email));
    }

    @Override
    public void savePerson(Person person) {
            personRepository.save(person);
    }


    @Override
    public void deletePerson(String email){
        personRepository.deleteByEmail(email);
    }

    @Override
    public void logicDeletePerson(String email) {
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException("No se encontró ninguna persona con el email: " + email));

        person.setActive(false);

        personRepository.save(person);
    }

    @Override
    public List<Person> getActivePersons() {
        List<Person> allPersons = personRepository.findAll();
        return allPersons.stream()
                .filter(Person::isActive)
                .collect(Collectors.toList());
    }

}
