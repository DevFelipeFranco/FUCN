package com.fucn.controller;

import com.fucn.domain.Device;
import com.fucn.domain.RequestType;
import com.fucn.dto.HttpResponse;
import com.fucn.dto.RequestTypeDTO;
import com.fucn.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@RestController
@RequestMapping(value = "/api/v1/requestType")
@RequiredArgsConstructor
public class RequestTypeController {

    private final RequestTypeService requestTypeService;

    @GetMapping
    public ResponseEntity<HttpResponse> findAll() {
        List<RequestType> all = requestTypeService.findAll();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("requestType", all))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    @PostMapping(value = "/create-request-type")
    public ResponseEntity<HttpResponse> createPerson(@RequestBody RequestTypeDTO requestTypeDTO) {
        RequestType requestType = requestTypeService.createRequestType(requestTypeDTO);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("requestType", requestType))
                        .message("Request type created successfully")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
    }

    private URI getUri() {
        return URI.create(fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }
}
