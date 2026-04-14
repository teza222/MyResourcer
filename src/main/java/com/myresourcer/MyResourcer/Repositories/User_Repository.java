package com.myresourcer.MyResourcer.Repositories;

import com.myresourcer.MyResourcer.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface User_Repository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
}
