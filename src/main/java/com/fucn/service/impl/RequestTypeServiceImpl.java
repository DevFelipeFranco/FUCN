package com.fucn.service.impl;

import com.fucn.domain.RequestType;
import com.fucn.repository.RequestTypeRepository;
import com.fucn.service.RequestTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RequestTypeServiceImpl implements RequestTypeService {

    private final RequestTypeRepository requestTypeRepository;

    @Override
    public List<RequestType> findAll() {
        return requestTypeRepository.findAll();
    }
}
