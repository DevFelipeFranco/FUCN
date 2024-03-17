package com.fucn.service.impl;

import com.fucn.domain.Device;
import com.fucn.domain.RequestType;
import com.fucn.dto.DeviceDTO;
import com.fucn.repository.DeviceRepository;
import com.fucn.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device createDevice(DeviceDTO deviceDTO) {
        Device build = Device.builder()
                .name(deviceDTO.name())
                .model(deviceDTO.model())
                .reference(deviceDTO.reference())
                .build();
        return deviceRepository.save(build);
    }
}
