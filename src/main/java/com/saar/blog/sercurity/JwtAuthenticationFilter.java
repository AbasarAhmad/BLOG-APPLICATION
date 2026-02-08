package com.saar.blog.sercurity;


import java.io.IOException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Read Authorization header
        String requestToken = request.getHeader("Authorization");

        // Example: Bearer eyJhbGciOiJIUzI1NiJ9...
        System.out.println(requestToken);

        String username = null;
        String token = null;

        // Check if token starts with Bearer
        if (requestToken != null && requestToken.startsWith("Bearer")) {

            // Remove "Bearer " from token
            token = requestToken.substring(7);

            try {
                // Extract username from token
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e) {
                // Token extraction failed
                System.out.println("Unable to get JWT token");
            }
            catch (ExpiredJwtException e) {
                // Token expired
                System.out.println("JWT token has expired");
            }
            catch (MalformedJwtException e) {
                // Token format invalid
                System.out.println("Invalid JWT token");
            }
        }
        else {
            // Header missing or not Bearer
            System.out.println("JWT token does not begin with Bearer");
        }

        // Validate token and set authentication
        if (username != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user from database
            UserDetails userDetails =
                    this.userDetailsService.loadUserByUsername(username);

            // Check token validity
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {

                // Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                // Attach request details
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Set user as authenticated
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            else {
                // Token validation failed
                System.out.println("Invalid JWT token");
            }
        }
        else {
            // Username missing or already authenticated
            System.out.println("Username is null or context is not empty");
        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}
