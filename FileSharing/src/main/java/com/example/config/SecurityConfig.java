package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoding for authentication
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF for simplicity (only for development; not recommended for production)
            .authorizeHttpRequests()
                .requestMatchers("/register", "/login", "/css/").permitAll() // Allow unauthenticated access to these paths
                .anyRequest().authenticated() // All other requests require authentication
            .and()
            .formLogin()
                .loginPage("/login") // Custom login page at /login
                .loginProcessingUrl("/perform_login") // URL to handle login POST requests
                .defaultSuccessUrl("/dashboard", true) // Redirect to /dashboard on successful login
                .failureUrl("/login?error=true") // Redirect to /login with error query param on failure
            .and()
            .logout()
                .logoutUrl("/perform_logout") // URL to trigger logout
                .logoutSuccessUrl("/login") // Redirect to /login on logout
                .deleteCookies("JSESSIONID"); // Remove session cookie on logout

        return http.build(); // Build the security filter chain
    }
}