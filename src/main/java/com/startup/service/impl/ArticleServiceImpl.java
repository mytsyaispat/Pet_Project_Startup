package com.startup.service.impl;

import com.startup.entity.Article;
import com.startup.entity.Category;
import com.startup.repository.ArticleRepository;
import com.startup.service.ArticleService;
import com.startup.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryService categoryService) {
        this.articleRepository = articleRepository;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public ResponseEntity<?> createArticle(Article article) {
        Category category = categoryService.getCategoryIfExistsElseCreate(article);
        articleRepository.save(article);
        categoryService.saveCategory(category);
        return ResponseEntity.ok("Article successfully added!");
    }

    @Override
    public List<Article> findArticlesForTheLastSevenDays() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysEgo = now.minusDays(6).truncatedTo(ChronoUnit.DAYS);
        return articleRepository.findAllByDateBetween(sevenDaysEgo, now);
    }

    @Override
    public ResponseEntity<List<Article>> responseArticleList() {
        List<Article> articleList = articleRepository.findAll();
        if (articleList.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(articleList);
    }

    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }

}
