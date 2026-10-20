package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.RegisterRequestDTO;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequestDTO data) {

        if(!data.getPassword().equals(data.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        if(userRepository.findByEmail(data.getEmail()).isPresent()) {
            throw new IllegalArgumentException("This email already registered!");
        }

        User newUser = new User();

        newUser.setName(data.getName());
        newUser.setEmail(data.getEmail());

        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        newUser.setPassword(encryptedPassword);
        newUser.setRole("USER");

        return userRepository.save(newUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

}
