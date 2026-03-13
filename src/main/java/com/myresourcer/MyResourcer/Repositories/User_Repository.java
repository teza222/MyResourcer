package com.myresourcer.MyResourcer.Repositories;

import com.myresourcer.MyResourcer.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_Repository extends JpaRepository<Users, Integer> {
}
