package com.movieticket.config;

import com.movieticket.service.CustomUserDetailsService; // Use Custom UserDetailsService

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor // ✅ Lombok automatically generates constructor with @Autowired fields
@EnableMethodSecurity
public class SecurityConfig {

    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Publicly accessible endpoints
                .requestMatchers("/error").permitAll() // Allow error endpoint
                .requestMatchers(HttpMethod.POST, "/api/register", "/api/login").permitAll()
                
            
                /// Only ADMIN can manage books
                .requestMatchers(HttpMethod.POST, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/books/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAuthority("ROLE_ADMIN")
    
                // Any authenticated user can view books
                .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()

                // USERS and ADMINS can place/view orders
                .requestMatchers("/api/orders/**").authenticated()

                // Any other request must be authenticated
                .anyRequest().authenticated()

                
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            System.out.println("Security Configuration Loaded: Restricting POST /api/books to ADMIN role");


            

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(List.of(authProvider));
    }

    
   /*  public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    } */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



// token for user-eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYW1wbGVAZXhhbXBsZS5jb20iLCJleHAiOjE3MzgyODgwODUsImlhdCI6MTczODI1MjA4NX0.zgnCc91jh8DA1O0MNJS590GlEv2Osy1G1H0U0chFlsk

//token for admin -eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYWRoYXZAZXhhbXBsZS5jb20iLCJyb2xlIjoiQURNSU4iLCJleHAiOjE3Mzg0MzI3NDQsImlhdCI6MTczODM5Njc0NH0.e7mKIF5z_HG-1Da65cbq2-zOUgxlVw3txDxRbTG3ddw
 