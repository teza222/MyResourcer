package com.myresourcer.MyResourcer.Repositories;

import com.myresourcer.MyResourcer.Models.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Department_Repository extends JpaRepository<Departments, Integer> {
}
