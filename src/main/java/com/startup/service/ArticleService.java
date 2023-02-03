package com.startup.service;

import com.startup.entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {

    ResponseEntity<?> createArticle(Article article);

    List<Article> findArticlesForTheLastSevenDays();
    ResponseEntity<List<Article>> responseArticleList();
    List<Article> getArticleList();

}
