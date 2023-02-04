package com.startup.Logic.service.impl;

import com.startup.Logic.entity.Category;
import com.startup.Logic.repository.CategoryRepository;
import com.startup.Logic.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * Метод создаёт категорию в базе данных если такой статьи ещё нет, в противном случае не создаёт
     * */
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
