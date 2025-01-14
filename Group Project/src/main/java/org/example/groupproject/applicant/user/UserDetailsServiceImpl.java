package org.example.groupproject.applicant.user;

import org.example.groupproject.applicant.security.LoginFailureListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginFailureListener loginFailureListener;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginFailureListener.isBlocked(username)) {
            throw new UsernameNotFoundException("blocked");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        loginFailureListener.loginSucceeded(username);
        return new UserDetailsImpl(user);
    }
}
