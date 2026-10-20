package com.example.ecommerceapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
