package com.myresourcer.MyResourcer.Repositories;

import com.myresourcer.MyResourcer.Models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Category_Repository extends JpaRepository<Categories, Integer> {
}
