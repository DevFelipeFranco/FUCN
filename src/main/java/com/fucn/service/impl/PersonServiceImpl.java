package com.fucn.service.impl;

import com.fucn.domain.Person;
import com.fucn.domain.User;
import com.fucn.domain.UserPrincipal;
import com.fucn.dto.PersonDTO;
import com.fucn.exception.ApiException;
import com.fucn.repository.PersonRepository;
import com.fucn.repository.UserRepository;
import com.fucn.service.PersonService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    @Override
    public Person createPerson(PersonDTO personDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        Person person1 = userRepository.findUserByUsername((String) authentication.getPrincipal())
                .map(user -> {
                            if (!personRepository.existsPersonByUser(user)) {
                                return Person.builder()
                                        .firstName(personDTO.firstName())
                                        .lastName(personDTO.lastName())
                                        .dateBirth(personDTO.dateBirth())
                                        .address(personDTO.address())
                                        .residence(personDTO.residence())
                                        .position(personDTO.position())
                                        .user(user)
                                        .build();
                            } else {
                                throw new ApiException("Ya existe una persona asociado al usuario con el que inicio sesion");
                            }
                        }
                ).orElseThrow(() -> new UsernameNotFoundException("User not found: " + authentication.getName()));


        Person save = personRepository.save(person1);
        return save;
    }

    @Override
    public Person findPersonByUser(Long idUser) {
        User user = User.builder().idUser(idUser).build();
        Optional<Person> personByUser = personRepository.findPersonByUser(user);
        return personByUser.orElse(null);
    }
}
