package com.espresso.dao.repository;

import com.espresso.dao.model.MenuOutline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOutlineRepository extends JpaRepository<MenuOutline, String> {
}