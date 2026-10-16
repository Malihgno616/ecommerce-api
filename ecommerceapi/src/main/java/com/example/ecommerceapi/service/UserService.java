package com.example.ecommerceapi.service;

import com.example.ecommerceapi.dto.RegisterRequestDTO;
import com.example.ecommerceapi.model.User;
import com.example.ecommerceapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        newUser.setPassword(data.getPassword());
        newUser.setRole("USER");

        return userRepository.save(newUser);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
