package com.fucn.dto;

import lombok.Builder;

@Builder
public record UserRegister(
        String username,
        String password,
        String email
) {
}
