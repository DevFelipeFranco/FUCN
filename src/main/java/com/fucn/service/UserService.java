package com.fucn.service;

import com.fucn.domain.User;
import com.fucn.dto.UserRegister;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(UserRegister userRegister);

    List<User> findAll();

    Optional<User> findUserByUsername(String username);

    User findUserByEmail(String email);

    Authentication authentication(String username, String password);
}
