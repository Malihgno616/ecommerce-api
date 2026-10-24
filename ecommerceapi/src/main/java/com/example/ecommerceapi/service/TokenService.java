package com.example.ecommerceapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder encoder;

    // Alterado para receber o username e a role do usuário
    public String generateToken(String username, String role) {
        Instant now = Instant.now();
        int expiration = 3600;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("eccomerce-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiration))
                .subject(username)
                .claim("scope", role) // Agora injeta a string correta (ex: "ADMIN")
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
