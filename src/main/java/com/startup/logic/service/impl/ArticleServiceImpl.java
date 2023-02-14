package com.startup.logic.service.impl;

import com.startup.auth.entity.User;
import com.startup.auth.service.UserService;
import com.startup.logic.controller.entity.ArticleRequest;
import com.startup.logic.entity.Article;
import com.startup.logic.entity.Category;
import com.startup.logic.repository.ArticleRepository;
import com.startup.logic.service.ArticleService;
import com.startup.logic.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryService categoryService, UserService userService) {
        this.articleRepository = articleRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    /**
     * Метод создает статью и добавляет в базу данных в случае, если указанная категория существует.
     */
    @Override
    @Transactional
    public ResponseEntity<String> createArticle(ArticleRequest articleRequest, String author) {
        Optional<Category> oCategory = categoryService.getCategoryByName(articleRequest.getCategory());
        if (oCategory.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
        Category category = oCategory.get();
        if (!category.getCategoryList().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Данную категорию нельзя использовать, тк у неё есть наследники!");
        User user = (User) userService.loadUserByUsername(author);
        Article article = new Article(articleRequest, category, user);
        articleRepository.save(article);
        return ResponseEntity.ok("Article successfully added!");
    }

    /**
     * Метод возврящает статьи за последнюю неделю
     */
    @Override
    public List<Article> findArticlesForTheLastSevenDays() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysEgo = now.minusDays(6).truncatedTo(ChronoUnit.DAYS);
        return articleRepository.findAllByDateBetween(sevenDaysEgo, now);
    }

    /**
     * Метод возврящает все статьи
     */
    @Override
    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }

    /**
     * Метод возврящает статью по id
     */
    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }
}
