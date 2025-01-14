package org.example.groupproject.applicant.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        // Check if the user is blocked
        Boolean isBlocked = (Boolean) request.getSession().getAttribute("isBlocked");

        // Add the block status to the model
        if (isBlocked != null && isBlocked) {
            model.addAttribute("isBlocked", true);
            // Remove the attribute to avoid displaying the message on subsequent requests
            request.getSession().removeAttribute("isBlocked");
        } else {
            model.addAttribute("isBlocked", false);
        }

        return "login";
    }
}
