package com.startup;

import com.startup.auth.entity.User;
import com.startup.auth.repository.UserRepository;
import com.startup.auth.service.UserService;
import com.startup.logic.controller.entity.ArticleRequest;
import com.startup.logic.entity.Article;
import com.startup.logic.entity.Category;
import com.startup.logic.repository.ArticleRepository;
import com.startup.logic.repository.CategoryRepository;
import com.startup.logic.service.ArticleService;
import com.startup.logic.service.CategoryService;
import com.startup.logic.service.StatisticsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private UserService userService;

    @Test
    @Order(10)
    void tryToCreateArticleWithoutCategoryInDB() {
        articleService.createArticle(new ArticleRequest("title", "content", "Category1"), "admin");
        Assertions.assertTrue(articleService.getArticleList().isEmpty());
    }

    @Test
    @Order(20)
    void addCategory() {
        categoryService.createCategory(new Category("Category1"));
        Assertions.assertNotNull(categoryRepository.findByName("Category1"));
    }

    @Test
    @Order(30)
    void addArticle() {
        articleService.createArticle(new ArticleRequest("title", "content", "Category1"), "admin");
        Assertions.assertFalse(articleService.getArticleList().isEmpty());
    }

    @Test
    @Order(40)
    void tryToAddSameCategory() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategory(new Category("Category1")));
    }

    @Test
    @Order(50)
    void registerTest() {
        userService.register(Values.userArray[1]);
        User user = userService.getUserByUsername("user1");
        Assertions.assertNotNull(user);
    }





    @Disabled
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void addCorrectArticles1(int counter) {
        articleService.createArticle(new ArticleRequest("title", "content", "Category1"), "user1");
        List<Article> articleList = articleService.getArticleList();
        Assertions.assertEquals(List.of(Values.correctArticles[counter]).toString(), articleList.toString());
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void addCorrectArticles2(int counter) {
        articleRepository.save(Values.correctArticles[counter]);
        List<Article> articleList = articleRepository.findAll();
        Assertions.assertNotEquals(List.of(Values.correctArticles[counter + 1]).toString(), articleList.toString());
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void tryToSaveIncorrectArticles(int counter) {
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            articleRepository.save(Values.incorrectArticles[counter]);
            articleRepository.findAll();
        });
    }

}
