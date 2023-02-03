package com.startup.service;

import com.startup.controller.entity.ArticleRequest;
import com.startup.entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleService {

    ResponseEntity<?> createArticle(ArticleRequest article, String author);

    List<Article> findArticlesForTheLastSevenDays();
    ResponseEntity<List<Article>> responseArticleList();
    List<Article> getArticleList();

    List<Article> getArticleListByCategory(Long categoryId);

}
