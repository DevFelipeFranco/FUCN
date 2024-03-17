package com.fucn.controller.device;

import com.fucn.domain.Device;
import com.fucn.domain.RequestType;
import com.fucn.dto.DeviceDTO;
import com.fucn.dto.HttpResponse;
import com.fucn.dto.RequestTypeDTO;
import com.fucn.service.DeviceService;
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
@RequestMapping(value = "/api/v1/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<HttpResponse> findAll() {
        List<Device> all = deviceService.findAll();
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("devices", all))
                        .status(OK)
                        .statusCode(OK.value())
                        .build());

    }

    @PostMapping(value = "/create-device")
    public ResponseEntity<HttpResponse> createPerson(@RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.createDevice(deviceDTO);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("device", device))
                        .message("Request type created successfully")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
    }

    private URI getUri() {
        return URI.create(fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }
}
