package com.example.springSecurity.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll() // Public endpoints accessible to everyone
                        .requestMatchers("/user/**").hasRole("USER") // User endpoints require USER role
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Admin endpoints require ADMIN role
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(Customizer.withDefaults()) // Default login form
                .logout(Customizer.withDefaults()); // Default logout

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Define USER details
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        // Define ADMIN details
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        // Return InMemoryUserDetailsManager
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder for secure password hashing
    }
}
