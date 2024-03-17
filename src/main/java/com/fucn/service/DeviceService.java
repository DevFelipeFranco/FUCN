package com.fucn.service;

import com.fucn.domain.Device;
import com.fucn.domain.RequestType;
import com.fucn.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {

    List<Device> findAll();

    Device createDevice(DeviceDTO deviceDTO);
}
