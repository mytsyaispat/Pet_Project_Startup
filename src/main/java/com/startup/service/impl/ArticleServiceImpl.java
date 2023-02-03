package com.startup.service.impl;

import com.startup.controller.entity.ArticleRequest;
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
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

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
    public ResponseEntity<?> createArticle(ArticleRequest articleRequest, String author) {
        Category category = categoryService.findByName(articleRequest.getCategory());
        if (category == null)
            return new ResponseEntity<>("Category not found!", HttpStatus.BAD_REQUEST);
        Article article = new Article(articleRequest, category, author);
        articleRepository.save(article);
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

    @Override
    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getArticleListByCategory(Long categoryId) {
        return articleRepository.findAllByCategoryId(categoryId);
    }
}
