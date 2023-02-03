package com.startup.controller.article;

import com.startup.entity.Article;
import com.startup.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("startup/")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("article")
    public ResponseEntity<?> createArticle(@Valid @RequestBody Article article, @AuthenticationPrincipal User user) {
        article.setAuthor(user.getUsername());
        return articleService.createArticle(article);
    }

    @GetMapping("articles")
    public ResponseEntity<List<Article>> getArticleList() {
        return articleService.responseArticleList();
    }

}
