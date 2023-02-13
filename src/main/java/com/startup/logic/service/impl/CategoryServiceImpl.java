package com.startup.logic.service.impl;

import com.startup.logic.entity.Category;
import com.startup.logic.controller.entity.CategoryLink;
import com.startup.logic.repository.CategoryRepository;
import com.startup.logic.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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


    /**
     * Метод создаёт категорию в базе данных
     * */
    @Override
    @Transactional
    public ResponseEntity<String> createCategory(Category category) {
        Optional<Category> oCategory = getCategoryByName(category.getName());
        if (oCategory.isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT, "This category already exists");
        categoryRepository.save(category);
        return ResponseEntity.ok("Category was successfully added!");
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Метод выстраивает иерархию (предок -> потомок) между категориями
     */
    @Override
    public ResponseEntity<String> createCategoryLink(CategoryLink categoryLink) {
        if (categoryLink.getChildName().equals(categoryLink.getParentName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Категория не может наследоваться от самой себя!");
        Optional<Category> optionalCategoryParent = getCategoryByName(categoryLink.getParentName());
        if (optionalCategoryParent.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category parent is not found");
        Category categoryParent = optionalCategoryParent.get();
        Optional<Category> optionalCategoryChild = getCategoryByName(categoryLink.getChildName());
        if (optionalCategoryChild.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category child is not found");
        Category categoryChild = optionalCategoryChild.get();
        List<Category> categoryParentList = categoryParent.getCategoryList();
        if (!categoryParentList.isEmpty())
            for (Category category : categoryParentList)
                if (category.getName().equals(categoryChild.getName()))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такая связь уже есть!");
        Optional<Category> isInherited = Optional.ofNullable(categoryChild.getCategory());
        if (isInherited.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category child уже учавствует в иерархии");
        Category currentCategory = categoryParent;
        while (true) {
            Optional<Category> optionalParent = Optional.ofNullable(currentCategory.getCategory());
            if (optionalParent.isPresent()) {
                currentCategory = optionalParent.get();
            } else {
                if (currentCategory.getName().equals(categoryChild.getName()))
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Category child уже является parent в данной иерархии!");
                else break;
            }
        }
        categoryChild.setCategory(categoryParent);
        categoryRepository.save(categoryChild);
        return ResponseEntity.ok("Link successfully create!");
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getCategoryListWhereParentIdIsNull() {
        return categoryRepository.findAllWhereParentIdIsNull();
    }
}
