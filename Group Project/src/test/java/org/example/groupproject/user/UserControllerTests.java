package org.example.groupproject.user;

import org.example.groupproject.applicant.user.User;
import org.example.groupproject.applicant.user.UserController;
import org.example.groupproject.applicant.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReturnHomePage() {
        String result = userController.returnHomePage();
        assertEquals("home", result);
    }

    @Test
    public void testShowRegistrationForm() {
        String result = userController.showRegistrationForm(model);
        assertEquals("signup_form", result);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testProcessRegister() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        when(userRepository.findAll()).thenReturn(List.of());

        String result = userController.processRegister(user);
        assertEquals("registration_success", result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testManageUsers() {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setIsAdmin(true);

        when(authentication.getName()).thenReturn("admin");
        when(userRepository.findByUsername("admin")).thenReturn(adminUser);

        String result = userController.manageUsers(model, authentication);
        assertEquals("manage_users", result);
        verify(model).addAttribute(eq("users"), anyList());
    }

    @Test
    public void testEditUser() {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setIsAdmin(true);

        User user = new User();
        user.setId(1);

        when(authentication.getName()).thenReturn("admin");
        when(userRepository.findByUsername("admin")).thenReturn(adminUser);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        String result = userController.editUser(model, 1, authentication);
        assertEquals("edit_user", result);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(List.of(user));

        String result = userController.updateUser(1, user, model);
        assertEquals("manage_users", result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setIsAdmin(true);

        User user = new User();
        user.setId(1);

        when(authentication.getName()).thenReturn("admin");
        when(userRepository.findByUsername("admin")).thenReturn(adminUser);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        String result = userController.deleteUser(1, model, authentication);
        assertEquals("manage_users", result);
        verify(userRepository).delete(any(User.class));
    }

    @Test
    public void testNewUser() {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setIsAdmin(true);

        when(authentication.getName()).thenReturn("admin");
        when(userRepository.findByUsername("admin")).thenReturn(adminUser);

        String result = userController.newUser(model, authentication);
        assertEquals("new_user", result);
        verify(model).addAttribute(eq("user"), any(User.class));
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        when(userRepository.findAll()).thenReturn(List.of());

        String result = userController.createUser(user, model);
        assertEquals("manage_users", result);
        verify(userRepository).save(any(User.class));
    }
}