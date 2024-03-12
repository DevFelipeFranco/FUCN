package com.fucn.service;

import com.fucn.domain.RequestType;
import com.fucn.dto.RequestTypeDTO;

import java.util.List;

public interface RequestTypeService {
    List<RequestType> findAll();

    RequestType createRequestType(RequestTypeDTO requestTypeDTO);
}
