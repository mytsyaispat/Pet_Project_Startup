package com.startup.logic.service.impl;

import com.startup.logic.entity.Category;
import com.startup.logic.repository.CategoryRepository;
import com.startup.logic.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    @Transactional
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Метод создаёт категорию в базе данных если такой статьи ещё нет, в противном случае не создаёт
     * */
    @Override
    @Transactional
    public ResponseEntity<String> createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()) == null) {
            categoryRepository.save(category);
            return ResponseEntity.ok("Category was successfully added!");
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "You are trying to add an existing category!");
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
