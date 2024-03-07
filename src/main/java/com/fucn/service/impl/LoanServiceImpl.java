package com.fucn.service.impl;

import com.fucn.domain.Device;
import com.fucn.domain.Loan;
import com.fucn.domain.Person;
import com.fucn.domain.RequestType;
import com.fucn.dto.LoanDTO;
import com.fucn.dto.PersonDTO;
import com.fucn.dto.RequestTypeDTO;
import com.fucn.exception.ApiException;
import com.fucn.repository.LoanRepository;
import com.fucn.repository.PersonRepository;
import com.fucn.service.LoanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public Loan createLoan(LoanDTO loanDTO) {
        RequestType requestType = RequestType.builder().idRequestType(loanDTO.idRequestType()).build();
        Device device = Device.builder().idDevice(loanDTO.idDevice()).build();

//        Person person = addDeviceToPersonById(loanDTO.idPerson(), device);
        Person person = personRepository
                .findAllById(Collections.singletonList(loanDTO.idPerson()))
                .stream().findFirst()
                .orElseThrow(() -> new ApiException("No existe la persona con el id: " + loanDTO.idPerson()));

        // TODO: HAcer validacion si el dispositivo ya esta asignado a la persona que esta haciendo el prestamo

        Loan build = Loan.builder()
                .description(loanDTO.description())
                .address(loanDTO.address())
                .requestType(requestType)
                .person(person)
                .device(device)
                .build();

        return loanRepository.save(build);
    }

//    private Person addDeviceToPersonById(Long idPerson, Device device) {
//        return personRepository.findAllById(Collections.singleton(idPerson)).stream().findFirst()
//                .map(person -> buildPerson(person, device)).orElseThrow(() -> new ApiException("No existe la persona con el id: " + idPerson));
//
//    }
//
//    private Person buildPerson(Person person, Device device) {
//        person.getDevices().add(device);
//        return person;
//    }
}
