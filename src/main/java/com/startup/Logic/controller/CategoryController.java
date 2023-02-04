package com.startup.Logic.controller;

import com.startup.Logic.entity.Category;
import com.startup.Logic.service.CategoryService;
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

    @PostMapping("category")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        return categoryService.createCategory(category);
    }

}
