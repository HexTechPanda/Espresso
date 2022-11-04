package com.espresso.dao.repository;

import com.espresso.dao.model.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, String> {
}