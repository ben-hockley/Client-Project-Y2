package org.example.groupproject.applicant.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.groupproject.applicant.security.LoginFailureListener;
import org.example.groupproject.applicant.user.UserBlockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private LoginFailureListener loginFailureListener;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");

        boolean isBlocked = false;

        if (username != null) {
            loginFailureListener.loginFailed(username); // Record the failed attempt
            isBlocked = loginFailureListener.isBlocked(username); // Check block status
        }

        // Store the block status in the session
        request.getSession().setAttribute("isBlocked", isBlocked);

        // Redirect to the login page
        response.sendRedirect("/login");
    }





}
