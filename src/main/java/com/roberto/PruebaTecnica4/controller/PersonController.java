package com.roberto.PruebaTecnica4.controller;

import com.roberto.PruebaTecnica4.dto.PersonDTO;
import com.roberto.PruebaTecnica4.model.Person;
import com.roberto.PruebaTecnica4.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {


    @Autowired
    PersonService personService;

    @PostMapping("/new")
    public ResponseEntity<String> createPerson(@RequestBody PersonDTO personDTO) {
        boolean exists = personService.existsByEMail(personDTO.getEmail());

        // Comprobación del formato del email
        if (!isValidEmail(personDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email proporcionado no cumple con el formato");
        }

        if (!isValidDNI(personDTO.getDni())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El DNI proporcionado no cumple con el formato (8 numeros y letra) ");
        }

        if (exists) {
            Person existingPerson = personService.findByEMail(personDTO.getEmail());

            if (existingPerson.isActive()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe una persona con ese email proporcionado");
            } else{

                if (existingPerson.getDni().equals(personDTO.getDni())){

                }

                existingPerson.setActive(true);

                personService.savePerson(existingPerson);
                return ResponseEntity.status(HttpStatus.CREATED).body("Ya existe una persona con ese email, se ha sido activada exitosamente, pero NO editada");
            }
        } else {
            Person person = new Person();
            person.setName(personDTO.getName());
            person.setLastName(personDTO.getLastName());
            person.setDni(personDTO.getDni());
            person.setEmail(personDTO.getEmail());

            person.setActive(true);
            personService.savePerson(person);
            return ResponseEntity.status(HttpStatus.CREATED).body("La persona ha sido creada exitosamente");
        }
    }

    @PutMapping("/edit/{email}")
    public ResponseEntity<String> editPerson(@PathVariable String email, @RequestBody PersonDTO personDTO) {
        Person existingPerson = personService.findByEMail(email);

        // Comprobación del formato del email
        if (!isValidEmail(personDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email proporcionado no cumple con el formato");
        }

        if (!isValidDNI(personDTO.getDni())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El DNI proporcionado no cumple con el formato (8 numeros y letra) ");
        }

        if (existingPerson == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna persona con el email proporcionado");
        } else {
            if (!existingPerson.isActive()) {
                existingPerson.setActive(true);
            }

            existingPerson.setName(personDTO.getName());
            existingPerson.setLastName(personDTO.getLastName());
            existingPerson.setDni(personDTO.getDni());
            existingPerson.setEmail(personDTO.getEmail());

            personService.savePerson(existingPerson);
            return ResponseEntity.status(HttpStatus.OK).body("La persona ha sido editada exitosamente");
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deletePerson(@PathVariable String email) {
        if (personService.existsByEMail(email) && personService.findByEMail(email).isActive()) {
            personService.logicDeletePerson(email);
            return ResponseEntity.status(HttpStatus.OK).body("La persona ha sido borrada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna persona con el email: " + email);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> findByEMail(@PathVariable String email) {
        Person person = personService.findByEMail(email);
        if (personService.existsByEMail(email) && person.isActive()) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró ninguna persona con el email proporcionado");
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAllPersons() {
        List<Person> persons = personService.getActivePersons();

        if (persons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se han encontrado personas activas");
        } else {
            return ResponseEntity.ok(persons);
        }
    }

    // Método para validar el formato de email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Método para validar el formato de DNI español
    private boolean isValidDNI(String dni) {
        String dniRegex = "^((\\d{8})([-]?)([A-Za-z]))$";
        return dni.matches(dniRegex);
    }

}