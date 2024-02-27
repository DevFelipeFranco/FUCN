package com.fucn.service.impl;

import com.fucn.domain.Role;
import com.fucn.domain.User;
import com.fucn.dto.UserRegister;
import com.fucn.exception.ApiException;
import com.fucn.repository.UserRepository;
import com.fucn.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

import static com.fucn.constant.FileConstant.DEFAULT_USER_IMAGE_PATH;
import static com.fucn.constant.UserConstant.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.security.authentication.UsernamePasswordAuthenticationToken.unauthenticated;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public User register(UserRegister userRegister) {
        validateNewUsernameAndEmail(userRegister, EMPTY);
        Role roleBase = Role.builder()
                .idRole(2L)
                .name("USER")
                .description("Standard user role with basic access")
                .build();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleBase);
        User user = User.builder()
                .username(userRegister.username())
                .password(encoder.encode(userRegister.password()))
                .email(userRegister.email())
                .profileImageUrl(getTemporaryProfileImageUrl(userRegister.username()))
                .joinDate(new Date())
                .isActive(true)
                .isNotLocked(true)
                .roles(roleSet)
                .build();
        User save = userRepository.save(user);

        return save;
    }

    private User validateNewUsernameAndEmail(UserRegister userRegister, String newUsername) {
        Optional<User> userByNewUsername = findUserByUsername(userRegister.username());
        User userByNewEmail = findUserByEmail(userRegister.email());
        if(StringUtils.isNotBlank(newUsername)) {
            Optional<User> currentUser = findUserByUsername(newUsername);
            if(currentUser.isEmpty()) {
                throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + newUsername);
            }
            if(userByNewUsername.isPresent() && !currentUser.get().getIdUser().equals(userByNewUsername.get().getIdUser())) {
                throw new UsernameNotFoundException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null && !currentUser.get().getIdUser().equals(userByNewEmail.getIdUser())) {
                throw new ApiException(EMAIL_ALREADY_EXISTS);
            }
            return currentUser.get();
        } else {
            if(userByNewUsername.isPresent()) {
                throw new UsernameNotFoundException(USERNAME_ALREADY_EXISTS);
            }
            if(userByNewEmail != null) {
                throw new ApiException(EMAIL_ALREADY_EXISTS);
            }
            return null;
        }
    }

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Authentication authentication(String username, String password) {
        try {
            return authenticationManager.authenticate(unauthenticated(username, password));
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException(exception.getMessage());
            //            throw new RuntimeException(exception.getMessage());
        }
    }
}
