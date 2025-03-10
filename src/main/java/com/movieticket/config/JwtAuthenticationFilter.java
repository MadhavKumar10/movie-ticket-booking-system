package com.movieticket.config;


import com.movieticket.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/error"); // Skip security filter for /error endpoint
    }



    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        System.out.println("Processing request: " + requestURI);
        
        // Skip JWT validation for public routes
        if (requestURI.equals("/api/register") || requestURI.equals("/api/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        // Extract JWT Token from Authorization Header
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7); // Remove "Bearer " prefix
                System.out.println("Received JWT Token: " + jwt);
                username = jwtUtil.extractUsername(jwt); // Extract username from token
                System.out.println("Extracted Username: " + username);
            } else {
                System.out.println("Authorization header missing or invalid");
            }
    
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("User Authenticated: " + userDetails.getUsername());
                } else {
                    System.out.println("JWT Token Validation Failed");
                }
            }
    
        } catch (Exception e) {
            System.out.println("JWT Authentication failed: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid JWT Token");
            return;
        }
 
        filterChain.doFilter(request, response);
    }
}