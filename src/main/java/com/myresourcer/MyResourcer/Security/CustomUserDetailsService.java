package com.myresourcer.MyResourcer.Security;

import com.myresourcer.MyResourcer.Models.Users;
import com.myresourcer.MyResourcer.Repositories.User_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private User_Repository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by their username in the database
        Users user = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Create a Spring Security User object with the user's data and role
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoleId().getRoleName().replace("ROLE_", ""))
                .build();
    }
}
