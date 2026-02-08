package com.saar.blog.sercurity;

import java.io.IOException;



import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // Called when user tries to access a protected API without valid login
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        // Send 401 Unauthorized response to client
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Access Denied!!!"
        );
    }
}
