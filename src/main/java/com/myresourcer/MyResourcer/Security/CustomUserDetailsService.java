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
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Create a Spring Security User object
        // Account is locked if flag >= 3
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoleId().getRoleName().replace("ROLE_", ""))
                .accountLocked(user.getFlag() >= 3)
                .build();
    }
}
