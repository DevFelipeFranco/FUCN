package com.fucn.controller;

import com.fucn.constant.SecurityConstant;
import com.fucn.domain.User;
import com.fucn.domain.UserPrincipal;
import com.fucn.dto.HttpResponse;
import com.fucn.dto.UserLoginDTO;
import com.fucn.dto.UserRegister;
import com.fucn.provider.JWTTokenProvider;
import com.fucn.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import static com.fucn.constant.FileConstant.TEMP_PROFILE_IMAGE_BASE_URL;
import static com.fucn.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    @GetMapping(value = "hi")
    public ResponseEntity<String> hi() {
        return ResponseEntity.ok("Hi");
    }

    @PostMapping(value = "login")
    public ResponseEntity<User> login(@RequestBody UserLoginDTO userLoginDTO) {
        Authentication authentication = userService.authentication(userLoginDTO.username(), userLoginDTO.password());
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        HttpHeaders jwtHeaders = getJwtHeader(principal);
        return ResponseEntity.ok().headers(jwtHeaders).body(principal.getUser());
    }

    @PostMapping(value = "register")
    public ResponseEntity<HttpResponse> register(@RequestBody UserRegister userRegister) {
        User register = userService.register(userRegister);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", register))
                        .message(String.format("User account created for user %s", register.getUsername()))
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build());
    }

    @GetMapping(path = "image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private URI getUri() {
        return URI.create(fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }

    @RequestMapping("/error")
    public ResponseEntity<HttpResponse> handleError(HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason("There is no mapping for a " + request.getMethod() + " request for this path on the server")
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .build());
    }

    private HttpHeaders getJwtHeader(UserPrincipal principal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(principal));
        return headers;
    }
}
