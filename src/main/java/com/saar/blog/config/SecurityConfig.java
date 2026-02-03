package com.saar.blog.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.saar.blog.sercurity.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	
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
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        // HttpSecurity ke andar se AuthenticationManagerBuilder le rahe hain
        // Ye builder batata hai ki authentication (login) kaise hoga
        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Yahan hum Spring Security ko bata rahe hain:
        // 1. User details kahan se load karni hai (database se)
        // 2. Password kaunsa encoder use karke check karna hai (BCrypt)
        authBuilder
            .userDetailsService(customUserDetailService) // DB se user load karega
            .passwordEncoder(passwordEncoder());         // password match karega

        // Sab configuration ke baad AuthenticationManager build kar rahe hain
        // Ye hi actual object hai jo login time pe use hota hai
        return authBuilder.build();
    }


    
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {	
    	return new BCryptPasswordEncoder();
    }
}
