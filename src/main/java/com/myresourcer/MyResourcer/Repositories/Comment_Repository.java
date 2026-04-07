package com.myresourcer.MyResourcer.Repositories;

import com.myresourcer.MyResourcer.Models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Comment_Repository extends JpaRepository<Comments, Integer> {
}
