package com.myresourcer.MyResourcer.Repositories;

import com.myresourcer.MyResourcer.Models.Assets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Asset_Repository extends JpaRepository<Assets, Integer> {
}
