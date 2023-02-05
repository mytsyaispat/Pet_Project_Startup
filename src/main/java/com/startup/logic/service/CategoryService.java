package com.startup.logic.service;

import com.startup.logic.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    List<Category> getCategoryList();
    void saveCategory(Category category);
    ResponseEntity<String> createCategory(Category category);
    Category findByName(String name);
}
