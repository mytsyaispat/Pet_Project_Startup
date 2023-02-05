package com.startup.logic.service;

import com.startup.logic.controller.entity.ArticleRequest;
import com.startup.logic.entity.Article;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ArticleService {

    ResponseEntity<?> createArticle(ArticleRequest article, String author);

    List<Article> findArticlesForTheLastSevenDays();
    ResponseEntity<List<Article>> responseArticleList();
    List<Article> getArticleList();

    List<Article> getArticleListByCategory(Long categoryId);

}
