//package com.startup;
//
//import com.startup.auth.entity.User;
//import com.startup.auth.repository.UserRepository;
//import com.startup.auth.service.UserService;
//import com.startup.logic.controller.entity.ArticleRequest;
//import com.startup.logic.entity.Category;
//import com.startup.logic.repository.ArticleRepository;
//import com.startup.logic.repository.CategoryRepository;
//import com.startup.logic.service.ArticleService;
//import com.startup.logic.service.CategoryService;
//import com.startup.logic.service.StatisticsService;
//import org.hibernate.TransactionException;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.TransactionSystemException;
//import org.springframework.web.server.ResponseStatusException;
//
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class IntegrationTest {
//    @Autowired
//    private ArticleRepository articleRepository;
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private CategoryService categoryService;
//    @Autowired
//    private ArticleService articleService;
//    @Autowired
//    private StatisticsService statisticsService;
//    @Autowired
//    private UserService userService;
//
//    private final String category = "Category1";
//
//    @Test
//    @Order(10)
//    @DisplayName(value = "test1 -> createArticle method from ArticleService")
//    void tryToCreateArticleWithoutCategoryInDBTest() {
//        articleService.createArticle(new ArticleRequest("title", "content", category), "admin");
//        Assertions.assertTrue(articleService.getArticleList().isEmpty());
//    }
//
//    @Test
//    @Order(20)
//    @DisplayName(value = "test2 -> createCategory method from CategoryService")
//    void addCategoryTest() {
//        categoryService.createCategory(new Category(category));
//        Assertions.assertNotNull(categoryRepository.findByName(category));
//    }
//
//    @Test
//    @Order(30)
//    @DisplayName(value = "test3 -> createArticle method from ArticleService")
//    void addArticleTest() {
//        articleService.createArticle(new ArticleRequest("title", "content", category), "admin");
//        Assertions.assertFalse(articleService.getArticleList().isEmpty());
//    }
//
//    @Test
//    @Order(40)
//    @DisplayName(value = "test4 -> createCategory method from CategoryService")
//    void tryToAddSameCategoryTest() {
//        Assertions.assertThrows(ResponseStatusException.class,
//                () -> categoryService.createCategory(new Category(category)));
//    }
//
//    @Test
//    @Order(50)
//    @DisplayName(value = "test5 -> register method from UserService")
//    void tryToRegisterUserWithCorrectDataTest() {
//        userService.register(Values.userArray[1]);
//        User user = userService.getUserByUsername("user1");
//        Assertions.assertNotNull(user);
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {0, 1, 2})
//    @Order(60)
//    @DisplayName(value = "test6-8 -> createArticle method from ArticleService")
//    void tryToAddIncorrectArticles(int counter) {
//        Assertions.assertThrows(TransactionSystemException.class, () -> {
//            articleService.createArticle(Values.incorrectArticlesForCreate[counter], "user1");
//        });
//    }
//
//}
