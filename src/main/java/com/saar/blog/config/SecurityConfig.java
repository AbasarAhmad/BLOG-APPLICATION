package com.saar.blog.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // This method defines the security filter chain for the application.
    // It replaces the older WebSecurityConfigurerAdapter approach.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // CSRF (Cross-Site Request Forgery) protection is being disabled here.
            // Disabling CSRF is common for REST APIs that are accessed by non-browser clients.
            .csrf(csrf -> csrf.disable())

            // This method is used to define which HTTP requests need to be authorized (secured).
            .authorizeHttpRequests(auth -> auth

                // This means that any HTTP request to the application
                // (like /home, /login, /api/posts, etc.) will require the user
                // to be authenticated (logged in).
                .anyRequest().authenticated()
            )

            // This enables HTTP Basic Authentication.
            // The browser will prompt for username and password.
            // withDefaults() applies Spring Security's default configuration.
            .httpBasic(withDefaults());

        // Builds and returns the configured SecurityFilterChain.
        return http.build();
    }
}
