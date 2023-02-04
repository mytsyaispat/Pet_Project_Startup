package com.startup.Logic.service;

import com.startup.Logic.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    List<Category> getCategoryList();
    void saveCategory(Category category);
    ResponseEntity<String> createCategory(Category category);
    Category findByName(String name);
}
