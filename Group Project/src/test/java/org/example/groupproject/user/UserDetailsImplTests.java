package org.example.groupproject.user;

import org.example.groupproject.applicant.user.User;
import org.example.groupproject.applicant.user.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDetailsImplTests {

    private User user;
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("test@example.com");
        user.setIsAdmin(true);

        userDetails = new UserDetailsImpl(user);
    }

    @Test
    public void testGetUsername() {
        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    public void testGetEmail() {
        assertEquals("test@example.com", ((UserDetailsImpl) userDetails).getEmail());
    }

    @Test
    public void testGetIsAdmin() {
        assertTrue(((UserDetailsImpl) userDetails).getIsAdmin());
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }
}
