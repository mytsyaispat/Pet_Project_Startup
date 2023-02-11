package com.startup.logic.service;

import com.startup.logic.controller.entity.CategoryLink;
import com.startup.logic.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategoryList();
    void saveCategory(Category category);
    ResponseEntity<String> createCategory(Category category);
    Optional<Category> findByName(String name);
    ResponseEntity<String> createCategoryChild(CategoryLink categoryLink);

}
