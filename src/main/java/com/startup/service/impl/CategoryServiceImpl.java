package com.startup.service.impl;

import com.startup.entity.Article;
import com.startup.entity.Category;
import com.startup.repository.CategoryRepository;
import com.startup.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public ResponseEntity<String> createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()) == null) {
            categoryRepository.save(category);
            return ResponseEntity.ok("Category was successfully added!");
        }
        return new ResponseEntity<>("You are trying to add an existing category!", HttpStatus.CONFLICT);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
