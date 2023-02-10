package com.startup.logic.service.impl;

import com.startup.auth.repository.RoleRepository;
import com.startup.logic.controller.entity.CategoryParent;
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
     * Метод создаёт категорию в базе данных
     * */
    @Override
    @Transactional
    public ResponseEntity<String> createCategory(Category category) {
//        if (cat)
//        categoryRepository.save(category);
//        return ResponseEntity.ok("Category was successfully added!");
        return null;
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Метод создаёт потомка у категории либо выбрасывает ResponseStatusException если категория не прошла валидацию
     */
    @Override
    public ResponseEntity<String> createCategoryParent(CategoryParent categoryParent) {
//        String categoryName = categoryParent.getCategory();
//        String parentName = categoryParent.getParent();
//        List<Category> categoryList = categoryRepository.findAllByName(categoryName);
//        if (categoryList.isEmpty())
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
//        if (categoryList.stream().anyMatch(category -> category.getParent().getName().equals(parentName)))
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "You are trying to add same parent!");
//        if (categoryList.size() == 1) {
//            Category category = categoryList.get(0);
//            category.setParent();
//            createCategory(category)
//        }
        return null;
    }
}
