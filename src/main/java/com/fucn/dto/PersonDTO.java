package com.fucn.dto;

import com.fucn.domain.User;
import lombok.Builder;

import java.util.Date;

@Builder
public record PersonDTO(
        String firstName,
        String lastName,
        Date dateBirth,
        String address,
        String residence,
        String position,
        User user

) {
}
