package com.startup.service;

import com.startup.entity.Article;
import com.startup.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {

    List<Category> getCategoryList();
    void saveCategory(Category category);
    ResponseEntity<String> createCategory(Category category);
    Category findByName(String name);
}
