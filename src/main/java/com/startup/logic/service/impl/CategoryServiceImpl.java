package com.startup.logic.service.impl;

import com.startup.logic.controller.entity.CategoryLink;
import com.startup.logic.entity.Category;
import com.startup.logic.repository.CategoryRepository;
import com.startup.logic.service.CategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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


    /**
     * Метод создаёт категорию в базе данных
     */
    @Override
    @CachePut(value = "category", key = "#category.name")
    public Category createCategory(Category category) {
        Optional<Category> oCategory = getCategoryByName(category.getName());
        if (oCategory.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This category already exists");
        categoryRepository.save(category);
        return category;
    }

    /**
     * Метод возвращает категорию по имени
     */
    @Override
    @Cacheable(value = "category")
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Метод выстраивает иерархию (предок -> потомок) между категориями
     */
    @Override
    @CacheEvict(value = "category", allEntries = true)
    public Category createCategoryLink(CategoryLink categoryLink) {
        if (categoryLink.getChildName().equals(categoryLink.getParentName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A category cannot inherit from itself!");
        Optional<Category> optionalCategoryParent = categoryRepository.findByName(categoryLink.getParentName());
        if (optionalCategoryParent.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category parent is not found");
        Category categoryParent = optionalCategoryParent.get();
        Optional<Category> optionalCategoryChild = categoryRepository.findByName(categoryLink.getChildName());
        if (optionalCategoryChild.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category child is not found");
        Category categoryChild = optionalCategoryChild.get();
        List<Category> categoryParentList = categoryParent.getCategoryList();
        if (!categoryParentList.isEmpty())
            for (Category category : categoryParentList)
                if (category.getName().equals(categoryChild.getName()))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This link already exists!");
        Optional<Category> isInherited = Optional.ofNullable(categoryChild.getCategory());
        if (isInherited.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category child already in the hierarchy");
        Category currentCategory = categoryParent;
        while (true) {
            Optional<Category> optionalParent = Optional.ofNullable(currentCategory.getCategory());
            if (optionalParent.isPresent()) {
                currentCategory = optionalParent.get();
            } else {
                if (currentCategory.getName().equals(categoryChild.getName()))
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Category child is already a parent in the given hierarchy!");
                else break;
            }
        }
        categoryChild.setCategory(categoryParent);
        return categoryRepository.save(categoryChild);
    }

    /**
     * Метод возвращает категорию по id
     */
    @Override
    @Cacheable(value = "category")
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Метод возвращает лист категорий у которых нет родителя
     */
    @Override
    public List<Category> getCategoryListWhereParentIdIsNull() {
        return categoryRepository.findAllWhereParentIdIsNull();
    }
}
