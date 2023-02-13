package com.startup.logic.controller;

import com.startup.logic.controller.entity.CategoryLink;
import com.startup.logic.entity.Category;
import com.startup.logic.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<String> createCategoryLink(@Valid @RequestBody CategoryLink categoryLink) {
        return categoryService.createCategoryLink(categoryLink);
    }

    @GetMapping("admin/category/{id}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.getCategoryById(id);
        if (categoryOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
        Category category = categoryOptional.get();
        return ResponseEntity.ok(Map.of("id", category.getId(), "name", category.getName()));
    }

    @GetMapping("categories/{id}")
    public ResponseEntity<Category> getCategoryHierarchy(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.getCategoryById(id);
        if (categoryOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
        return ResponseEntity.ok(categoryOptional.get());
    }

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategoryHierarchy() {
        List<Category> categoryList = categoryService.getCategoryListWhereParentIdIsNull();
        if (categoryList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(categoryList);
    }


}
