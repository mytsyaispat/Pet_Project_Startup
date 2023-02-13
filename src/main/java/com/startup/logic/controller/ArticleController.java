package com.startup.logic.controller;

import com.startup.logic.controller.entity.ArticleRequest;
import com.startup.logic.entity.Article;
import com.startup.logic.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("article")
    public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleRequest articleRequest, @AuthenticationPrincipal User user) {
        return articleService.createArticle(articleRequest, user.getUsername());
    }

    @GetMapping("articles")
    public ResponseEntity<List<Article>> getArticleList() {
        List<Article> articleList = articleService.getArticleList();
        if (articleList.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(articleList);
    }

    @GetMapping("article/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> articleOptional = articleService.getArticleById(id);
        if (articleOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found!");
        return ResponseEntity.ok(articleOptional.get());
    }

}
