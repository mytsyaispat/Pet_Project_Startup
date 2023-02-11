package com.startup.logic.service.impl;

import com.startup.logic.controller.entity.ArticleRequest;
import com.startup.logic.entity.Article;
import com.startup.logic.entity.Category;
import com.startup.logic.repository.ArticleRepository;
import com.startup.logic.service.ArticleService;
import com.startup.logic.service.CategoryService;
import com.startup.auth.entity.User;
import com.startup.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
     * */
    @Override
    @Transactional
    public ResponseEntity<?> createArticle(ArticleRequest articleRequest, String author) {
        Optional<Category> oCategory = categoryService.findByName(articleRequest.getCategory());
        if (oCategory.isEmpty())
            return new ResponseEntity<>("Category not found!", HttpStatus.BAD_REQUEST);
        Category category = oCategory.get();
        User user = userService.getUserByUsername(author);
        Article article = new Article(articleRequest, category, user);
        articleRepository.save(article);
        return ResponseEntity.ok("Article successfully added!");
    }

    /**
     * Метод возврящает статьи за последнюю неделю
     * */
    @Override
    public List<Article> findArticlesForTheLastSevenDays() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysEgo = now.minusDays(6).truncatedTo(ChronoUnit.DAYS);
        return articleRepository.findAllByDateBetween(sevenDaysEgo, now);
    }

    /**
     * Метод возврящает все статьи или пустой лист, если их нет как ответ
     * */
    @Override
    public ResponseEntity<List<Article>> responseArticleList() {
        List<Article> articleList = articleRepository.findAll();
        if (articleList.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(articleList);
    }

    /**
     * Метод возврящает все статьи
     * */
    @Override
    public List<Article> getArticleList() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> getArticleListByCategory(Long categoryId) {
        return articleRepository.findAllByCategoryId(categoryId);
    }
}
