package com.fucn.dto;

import lombok.Builder;

@Builder
public record DeviceDTO(
        Long idDevice,
        String name,
        String model,
        String reference
) {
}
