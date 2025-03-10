package com.movieticket.controller;

import com.movieticket.dto.AuthRequest;
import com.movieticket.dto.AuthResponse;
import com.movieticket.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // Register a new user (Customer or Admin)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    // Authenticate an existing user and return a JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticateUser(request));
    }
}