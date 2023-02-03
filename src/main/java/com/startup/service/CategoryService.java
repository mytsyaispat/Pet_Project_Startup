package com.startup.service;

import com.startup.entity.Article;
import com.startup.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> getCategoryList();
    void saveCategory(Category category);
    Category getCategoryIfExistsElseCreate(Article article);
}
