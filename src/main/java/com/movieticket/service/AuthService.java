package com.movieticket.service;

import com.movieticket.dto.AuthRequest;
import com.movieticket.dto.AuthResponse;
import com.movieticket.entity.User;
import com.movieticket.repository.UserRepository;
import com.movieticket.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String registerUser(AuthRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Ensure the role is set correctly
        try {
            user.setRole(User.Role.valueOf(request.getRole().toUpperCase())); // Convert string to enum
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role. Allowed values: 'CUSTOMER' or 'ADMIN'");
        }

        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponse authenticateUser(AuthRequest request) {
        System.out.println("Authenticating user: " + request.getEmail());

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            System.out.println("Authentication success for: " + request.getEmail());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication successful");

            Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
            if (userOptional.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            User user = userOptional.get();
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name()); // Use enum's name() method

            System.out.println("Generated Token: " + token); // Debugging

            return new AuthResponse(token, user.getRole().name()); // Use enum's name() method

        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid email or password");
        }
    }
}