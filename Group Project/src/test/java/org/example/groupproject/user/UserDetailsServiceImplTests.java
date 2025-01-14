//package org.example.groupproject.user;
//
//import org.example.groupproject.applicant.user.User;
//import org.example.groupproject.applicant.user.UserDetailsServiceImpl;
//import org.example.groupproject.applicant.user.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//
//public class UserDetailsServiceImplTests {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserDetailsServiceImpl userDetailsService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testLoadUserByUsername() {
//        User user = new User();
//        user.setUsername("testUser");
//        user.setPassword("password");
//        user.setEmail("test@example.com");
//        user.setIsAdmin(true);
//
//        when(userRepository.findByUsername("testUser")).thenReturn(user);
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");
//        assertEquals("testUser", userDetails.getUsername());
//        assertEquals("password", userDetails.getPassword());
//    }
//
//    @Test
//    public void testLoadUserByUsernameNotFound() {
//        when(userRepository.findByUsername("unknownUser")).thenReturn(null);
//
//        assertThrows(UsernameNotFoundException.class, () -> {
//            userDetailsService.loadUserByUsername("unknownUser");
//        });
//    }
//}
