package com.myresourcer.MyResourcer.Config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF to allow POST/PUT/DELETE requests from Postman without tokens
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((requests) -> requests
                // Permit public access to web pages and static resources
                .requestMatchers("/", "/register", "/login", "/forgot-password", "/reset-password", "/css/**", "/js/**", "/images/**").permitAll()
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            // Configure HTTP Basic authentication
            .httpBasic(basic -> basic.authenticationEntryPoint((request, response, authException) -> {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
            }))
            // Enable Form Login for browser-based access
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
