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
@RequestMapping("articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<String> createArticle(@Valid @RequestBody ArticleRequest articleRequest, @AuthenticationPrincipal User user) {
        articleService.createArticle(articleRequest, user.getUsername());
        return new ResponseEntity<>("Article successfully added!", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Article>> getArticleList() {
        List<Article> articleList = articleService.getArticleList();
        return ResponseEntity.ok(articleList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Optional<Article> articleOptional = articleService.getArticleById(id);
        if (articleOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found!");
        return ResponseEntity.ok(articleOptional.get());
    }

}
