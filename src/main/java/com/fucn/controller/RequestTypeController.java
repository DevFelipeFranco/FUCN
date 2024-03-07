package com.fucn.controller;

import com.fucn.domain.Device;
import com.fucn.domain.RequestType;
import com.fucn.dto.HttpResponse;
import com.fucn.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

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
}
