package org.example.groupproject.applicant.security;

import org.example.groupproject.applicant.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final String[] ENDPOINTS_WHITELIST = {
            "/applicants/**",
            "/applicantForm",
            "importcsv",
            "import-csv",
            "/cv/**",
            "/manageUsers",
            "/editUser/**",
            "/updateUser/**",
            "/reports",
    };


    @Bean
    UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());
        
        http.headers(header-> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        //http.headers(header->{header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);});

        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers(ENDPOINTS_WHITELIST).authenticated()
                                .anyRequest().permitAll()
                )
                .formLogin(login ->
                        login.usernameParameter("username")
                                .defaultSuccessUrl("/applicants/all")
                                .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll()
                );

        return http.build();
    }
}
