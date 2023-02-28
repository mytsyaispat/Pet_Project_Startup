package com.startup.logic.service;

import com.startup.logic.controller.entity.CategoryLink;
import com.startup.logic.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getCategoryList();

    Category createCategory(Category category);

    Optional<Category> getCategoryByName(String name);

    Category createCategoryLink(CategoryLink categoryLink);

    Optional<Category> getCategoryById(Long id);

    List<Category> getCategoryListWhereParentIdIsNull();
}
