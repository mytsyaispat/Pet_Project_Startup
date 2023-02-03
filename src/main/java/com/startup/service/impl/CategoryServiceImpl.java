package com.startup.service.impl;

import com.startup.entity.Article;
import com.startup.entity.Category;
import com.startup.repository.CategoryRepository;
import com.startup.service.CategoryService;
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

    public Category getCategoryIfExistsElseCreate(Article article) {
        Category category = categoryRepository.findByName(article.getCategory());
        if (category != null) {
            category.addArticleToList(article);
        } else {
            category = new Category(article);
        }
        return category;
    }

}
