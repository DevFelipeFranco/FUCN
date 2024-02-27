package com.fucn.controller.person;

import com.fucn.domain.Person;
import com.fucn.dto.HttpResponse;
import com.fucn.dto.PersonDTO;
import com.fucn.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@RestController
@RequestMapping(value = "/api/v1/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping(value = "/hi")
    public ResponseEntity<String> hi() {
        return ResponseEntity.ok("hi!");
    }

    @PostMapping(value = "/create-person")
    public ResponseEntity<HttpResponse> createPerson(@RequestBody PersonDTO personDTO) {
        Person person = personService.createPerson(personDTO);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("person", person))
                        .message(String.format("Person created for person %s", person.getFirstName()))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
    }

    private URI getUri() {
        return URI.create(fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }
}
