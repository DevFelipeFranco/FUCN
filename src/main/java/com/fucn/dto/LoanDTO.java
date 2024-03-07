package com.fucn.dto;

import lombok.Builder;

@Builder
public record LoanDTO(
        Long id,
        String description,
        String address,
        Long idPerson,
        Long idRequestType,
        Long idDevice
) {
}
