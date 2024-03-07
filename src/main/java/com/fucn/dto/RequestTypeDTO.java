package com.fucn.dto;

import lombok.Builder;

@Builder
public record RequestTypeDTO(
        Long idRequestType,
        String name,
        String description
) {
}
