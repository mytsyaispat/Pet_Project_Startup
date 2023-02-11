package com.startup.logic.controller;

import com.startup.logic.entity.Category;
import com.startup.logic.controller.entity.CategoryLink;
import com.startup.logic.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("admin/category")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PostMapping("admin/category-link")
    public ResponseEntity<?> createCategoryLink(@Valid @RequestBody CategoryLink categoryLink) {
        return categoryService.createCategoryChild(categoryLink);
    }


}
