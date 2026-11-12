package com.example.student.security;

import com.example.student.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void testTokenGenerationAndValidation() {
        String username = "test@mail.ru";
        String token = jwtService.generateToken(username, Set.of(Role.ADMIN));

        UserDetails userDetails = User.withUsername(username)
                .password("pass").authorities("ROLE_ADMIN").build();

        assertTrue(jwtService.validateToken(token, userDetails));
        assertEquals(username, jwtService.extractUsername(token));
        assertTrue(jwtService.extractRoles(token).contains(Role.ADMIN));
    }
}