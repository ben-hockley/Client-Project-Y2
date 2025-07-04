package org.example.groupproject.applicant.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String returnHomePage() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {

        if (userRepository.findAll().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            return "signup_form"; // sends user back to signup form if the username is already taken.
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "registration_success";
    }

    @GetMapping("/manageUsers")
    public String manageUsers(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (!user.getIsAdmin()) {
            model.addAttribute("error", "You must be an admin to access this page");
            return "access_denied";
        }

        model.addAttribute("users", userRepository.findAll());
        return "manage_users";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(Model model, @PathVariable Integer id, Authentication authentication) {
        String username = authentication.getName();
        User activeUser = userRepository.findByUsername(username);
        if (!activeUser.getIsAdmin()) {
            model.addAttribute("error", "You must be an admin to access this page");
            return "access_denied";
        }
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable Integer id, User user, Model model) {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userToUpdate.setUsername(user.getUsername());

        if (userRepository.findAll().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()) && !Objects.equals(u.getId(), id))) {
            model.addAttribute("error", "Username already taken");
            return "edit_user";
        } else if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()) && !Objects.equals(u.getId(), id))) {
            model.addAttribute("error", "Email already taken");
            return "edit_user";
        }
        // If the password field is not empty, encrypt and update the password
        if (user.getPassword().length() > 5) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            userToUpdate.setPassword(encodedPassword);
        } else if (!user.getPassword().isEmpty()) {
            model.addAttribute("error", "Password not updated, passwords must be at least 6 characters long");
        }

        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setIsAdmin(user.getIsAdmin());
        userRepository.save(userToUpdate);
        model.addAttribute("users", userRepository.findAll());
        return "manage_users";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Integer id, Model model, Authentication authentication) {
        String username = authentication.getName();
        User activeUser = userRepository.findByUsername(username);
        if (!activeUser.getIsAdmin()) {
            model.addAttribute("error", "You must be an admin to delete users");
            return "access_denied";
        }
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "manage_users";
    }

    @GetMapping("/newUser")
    public String newUser(Model model, Authentication authentication) {
        String username = authentication.getName();
        User activeUser = userRepository.findByUsername(username);
        if (!activeUser.getIsAdmin()) {
            model.addAttribute("error", "You must be an admin to create new users");
            return "access_denied";
        }
        model.addAttribute("user", new User());
        return "new_user";
    }

    @PostMapping("/createUser")
    public String createUser(User user, Model model) {
        if (userRepository.findAll().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            model.addAttribute("error", "Username already taken");
            return "new_user";
        } else if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            model.addAttribute("error", "Email already taken");
            return "new_user";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "manage_users";
    }
}
