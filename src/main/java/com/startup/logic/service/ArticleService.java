package com.startup.logic.service;

import com.startup.logic.controller.entity.ArticleRequest;
import com.startup.logic.entity.Article;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface ArticleService {

    ResponseEntity<String> createArticle(ArticleRequest article, String author);

    List<Article> findArticlesForTheLastSevenDays();

    List<Article> getArticleList();

    Optional<Article> getArticleById(Long id);
}
