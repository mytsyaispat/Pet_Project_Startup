package com.startup.logic.service.impl;

import com.startup.logic.entity.Category;
import com.startup.logic.controller.entity.CategoryLink;
import com.startup.logic.repository.CategoryRepository;
import com.startup.logic.service.CategoryService;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        Optional<Category> oCategory = findByName(category.getName());
        if (oCategory.isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT, "This category already exists");
        categoryRepository.save(category);
        return ResponseEntity.ok("Category was successfully added!");
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Метод выстраивает иерархию (предок -> потомок) между категориями
     */
    @Override
    public ResponseEntity<String> createCategoryChild(CategoryLink categoryLink) {
        if (categoryLink.getChildName().equals(categoryLink.getParentName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Категория не может наследоваться от самой себя!");
        Optional<Category> optionalCategoryParent = findByName(categoryLink.getParentName());
        if (optionalCategoryParent.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category parent is not found");
        Category categoryParent = optionalCategoryParent.get();
        Optional<Category> optionalCategoryChild = findByName(categoryLink.getChildName());
        if (optionalCategoryChild.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category child is not found");
        Category categoryChild = optionalCategoryChild.get();
        Optional<Category> categoryOptional = Optional.ofNullable(categoryChild.getCategory());
        if (categoryOptional.isPresent()) {
            if (categoryChild.getCategory().getName().equals(categoryParent.getName()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такая связь уже есть!");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Эта категория уже учавствует в иерархии");
        }
        Category categoryCurrent = categoryParent;
        while (true) {
            categoryOptional = Optional.ofNullable(categoryCurrent.getCategory());
            if (categoryOptional.isPresent()) {
                categoryCurrent = categoryCurrent.getCategory();
                if (categoryCurrent.getName().equals(categoryChild.getName()))
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Указанная категория child уже является parent в данной иерархии!");
            } else {
                break;
            }
        }
        categoryChild.setCategory(categoryParent);
        categoryRepository.save(categoryChild);
        return ResponseEntity.ok("Link successfully create!");
    }
}
