package com.startup.Logic.service;

import com.startup.Logic.controller.entity.ArticleRequest;
import com.startup.Logic.entity.Article;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ArticleService {

    ResponseEntity<?> createArticle(ArticleRequest article, String author);

    List<Article> findArticlesForTheLastSevenDays();
    ResponseEntity<List<Article>> responseArticleList();
    List<Article> getArticleList();

    List<Article> getArticleListByCategory(Long categoryId);

}
