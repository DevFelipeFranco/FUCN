package com.fucn.service;

import com.fucn.domain.Person;
import com.fucn.dto.PersonDTO;

public interface PersonService {

    Person createPerson(PersonDTO personDTO);

    Person findPersonByUser(Long idUser);
}
