package com.example.ecommerceapi.controller;

import com.example.ecommerceapi.dto.LoginRequestDTO;
import com.example.ecommerceapi.dto.RegisterRequestDTO;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.service.TokenService;
import com.example.ecommerceapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO data) {
        try {
            Map<String, String> response = new HashMap<>();

            userService.registerUser(data);

            response.put("message", "User registered successfully!!");
            response.put("status_code", String.valueOf(HttpStatus.CREATED.value()));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO data) {
        try {
            Map<String, String> response = new HashMap<>();

            User user = userService.findByEmail(data.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password!"));

            if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Invalid email or password!");
            }

            String token = tokenService.generateToken(user.getEmail(), user.getRole());

            response.put("access_token", token);
            response.put("token_type", "Bearer");

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
