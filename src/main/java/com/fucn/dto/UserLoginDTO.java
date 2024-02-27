package com.fucn.dto;

import lombok.Builder;

@Builder
public record UserLoginDTO(
        String username,
        String password
) {
}
