package com.startup;

import com.startup.auth.entity.Role;
import com.startup.auth.entity.User;
import com.startup.auth.service.RoleService;
import com.startup.auth.service.UserService;
import com.startup.logic.controller.entity.ArticleRequest;
import com.startup.logic.controller.entity.CategoryLink;
import com.startup.logic.entity.Article;
import com.startup.logic.entity.Category;
import com.startup.logic.service.ArticleService;
import com.startup.logic.service.CategoryService;
import com.startup.logic.service.StatisticsService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IntegrationTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private RoleService roleService;

    @Test
    @Order(5)
    @DisplayName(value = "-> Создание нескольких категорий и проверка на добавление в базу")
    void createCategory1() {
        categoryService.createCategory(new Category("Машины"));
        categoryService.createCategory(new Category("Внедорожники"));
        categoryService.createCategory(new Category("Легковые машины"));
        List<Category> categoryList = categoryService.getCategoryList();
        Assertions.assertEquals(3, categoryList.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Машины", "Внедорожники", "Легковые машины"})
    @Order(10)
    @DisplayName(value = "-> Проверка добавленных категорий, выборка по имени")
    void getCategoryByName(String categoryName) {
        Optional<Category> categoryOptional = categoryService.getCategoryByName(categoryName);
        Assertions.assertTrue(categoryOptional.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Машины", "Внедорожники", "Легковые машины"})
    @Order(13)
    @DisplayName(value = "-> Попытка добавить существующую категорию")
    void createCategoryShouldThrowException(String categoryName) {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategory(new Category(categoryName)));
    }

    @Test
    @Order(15)
    @DisplayName(value = "-> Попытка наследования от самой себя")
    void createCategoryLinkShouldThrowException1() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink("Машины", "Машины")));
    }

    @Test
    @Order(20)
    @DisplayName(value = "-> Указание несуществующей parent категории")
    void createCategoryLinkShouldThrowException2() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink("Чебупель", "Легковые машины")));
    }

    @Test
    @Order(25)
    @DisplayName(value = "-> Указание несуществующей child категории")
    void createCategoryLinkShouldThrowException3() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink("Машины", "Фрикадельки")));
    }

    @ParameterizedTest
    @CsvSource({"Машины,Легковые машины", "Машины,Внедорожники"})
    @Order(30)
    @DisplayName(value = "-> Корректное добавление связей между категориями")
    void createCategoryLink(String parent, String child) {
        Assertions.assertEquals(ResponseEntity.ok("Link successfully create!"),
                categoryService.createCategoryLink(new CategoryLink(parent, child)));
    }

    @ParameterizedTest
    @CsvSource({"Машины,Легковые машины", "Машины,Внедорожники"})
    @Order(35)
    @DisplayName(value = "-> Попытка добавить существующую связь")
    void createCategoryLinkShouldThrowException4(String parent, String child) {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink(parent, child)));
    }

    @ParameterizedTest
    @CsvSource({"Легковые машины,Внедорожники", "Внедорожники,Легковые машины"})
    @Order(40)
    @DisplayName(value = "-> Попытка использовать категорию child в качестве child у другой категории")
    void createCategoryLinkShouldThrowException5(String parent, String child) {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink(parent, child)));
    }

    @ParameterizedTest
    @CsvSource({"Легковые машины,Машины", "Внедорожники,Машины"})
    @Order(45)
    @DisplayName(value = "-> Попытка использовать категорию parent в качестве child у своих же child")
    void createCategoryLinkShouldThrowException6(String parent, String child) {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink(parent, child)));
    }

    @Test
    @Order(50)
    @DisplayName(value = "-> Проверка метода на возвращение категорий у которых нет parent")
    void getCategoryListWhereParentIdIsNull() {
        addSomeCorrectCategories();
        addSomeCorrectLinks();
        Assertions.assertEquals(3, categoryService.getCategoryListWhereParentIdIsNull().size());
    }

    void addSomeCorrectCategories() {
        categoryService.createCategory(new Category("Легковая машина №1"));
        categoryService.createCategory(new Category("Легковая машина №2"));
        categoryService.createCategory(new Category("Легковая машина №3"));
        categoryService.createCategory(new Category("Легковая машина №4"));
        categoryService.createCategory(new Category("Внедорожная машина №1"));
        categoryService.createCategory(new Category("Внедорожная машина №2"));
        categoryService.createCategory(new Category("Внедорожная машина №3"));
        categoryService.createCategory(new Category("Еда"));
        categoryService.createCategory(new Category("Random category 1"));
        categoryService.createCategory(new Category("Random category 2"));
        categoryService.createCategory(new Category("Random category 3"));
    }

    void addSomeCorrectLinks() {
        categoryService.createCategoryLink(new CategoryLink("Легковые машины", "Легковая машина №1"));
        categoryService.createCategoryLink(new CategoryLink("Легковые машины", "Легковая машина №2"));
        categoryService.createCategoryLink(new CategoryLink("Легковые машины", "Легковая машина №3"));
        categoryService.createCategoryLink(new CategoryLink("Легковые машины", "Легковая машина №4"));
        categoryService.createCategoryLink(new CategoryLink("Внедорожники", "Внедорожная машина №1"));
        categoryService.createCategoryLink(new CategoryLink("Внедорожники", "Внедорожная машина №2"));
        categoryService.createCategoryLink(new CategoryLink("Внедорожники", "Внедорожная машина №3"));
        categoryService.createCategoryLink(new CategoryLink("Random category 1", "Random category 2"));
        categoryService.createCategoryLink(new CategoryLink("Random category 1", "Random category 3"));
    }

    @Test
    @Order(55)
    @DisplayName(value = "-> Попытка добавить статью с несуществующей категорией")
    void createArticleShouldThrowException1() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> articleService.createArticle(new ArticleRequest("Это моя история про машину", "Бла-бла-бла", "Несуществующая категория про машину"), "admin"));
    }

    @Test
    @Order(55)
    @DisplayName(value = "-> Попытка добавить статью с категорией у которой есть потомки")
    void createArticleShouldThrowException2() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> articleService.createArticle(new ArticleRequest("Это моя история про машину", "Бла-бла-бла", "Машины"), "admin"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1", "user2"})
    @Order(57)
    @DisplayName(value = "-> Добавление новых пользователей")
    void createUser(String username) {
        Assertions.assertEquals(ResponseEntity.ok("User successfully registered!"),
                userService.createUser(new User(username, username)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1", "user2"})
    @Order(58)
    @DisplayName(value = "-> Попытка создать существующих пользователей")
    void createUserShouldThrowException(String username) {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> userService.createUser(new User(username, username)));
    }

    @Test
    @Order(60)
    @DisplayName(value = "-> Добавление категории")
    void createArticle() {
        Assertions.assertEquals(ResponseEntity.ok("Article successfully added!"),
                articleService.createArticle(new ArticleRequest("Это моя история про машину", "Бла-бла-бла", "Легковая машина №1"), "admin"));
        addSomeArticles();
    }

    void addSomeArticles() {
        articleService.createArticle(new ArticleRequest("Название", "Контент", "Легковая машина №1"), "admin");
        articleService.createArticle(new ArticleRequest("Название", "Контент", "Легковая машина №2"), "admin");
        articleService.createArticle(new ArticleRequest("Название", "Контент", "Легковая машина №2"), "user1");
        articleService.createArticle(new ArticleRequest("Название", "Контент", "Легковая машина №2"), "user1");
        articleService.createArticle(new ArticleRequest("Название", "Контент", "Легковая машина №1"), "user1");
        articleService.createArticle(new ArticleRequest("Название", "Контент", "Легковая машина №3"), "user1");
        articleService.createArticle(new ArticleRequest("Название", "Контент", "Легковая машина №1"), "user2");
        articleService.createArticle(new ArticleRequest("Название", "Контент", "Легковая машина №1"), "user2");
    }

    @Test
    @Order(65)
    @DisplayName(value = "-> Проверка на добавление категорий")
    void getArticleList() {
        List<Article> articleList = articleService.getArticleList();
        Assertions.assertEquals(9, articleList.size());
    }

    @Test
    @Order(65)
    @DisplayName(value = "-> Проверка на работоспасобность метода")
    void findArticlesForTheLastSevenDays() {
        List<Article> articleList = articleService.findArticlesForTheLastSevenDays();
        Assertions.assertEquals(9, articleList.size());
    }

    @ParameterizedTest
    @CsvSource(value = {",Content", "Title,",
            "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111symbols101,Content"})
    @Order(70)
    @DisplayName(value = "-> ")
    void createArticleShouldThrowException3(String title, String content) {
        Assertions.assertThrows(Exception.class,
                () -> articleService.createArticle(new ArticleRequest(title, content, "Легковая машина #1"), "admin"));
    }

    @Test
    @Order(75)
    @DisplayName("getStatisticsByCategory -> Проверка метода на работоспособность")
    void getStatisticsByCategory() {
        Assertions.assertEquals(Map.of("Легковая машина №1", 5L, "Легковая машина №2", 3L, "Легковая машина №3", 1L, "Легковая машина №4", 0L,
                        "Внедорожная машина №1", 0L, "Внедорожная машина №2", 0L, "Внедорожная машина №3", 0L,
                        "Random category 2", 0L, "Random category 3", 0L, "Еда", 0L),
                statisticsService.getStatisticsByCategory());
    }

    @Test
    @Order(75)
    @DisplayName("getStatisticsByAuthor -> Проверка метода на работоспособность")
    void getStatisticsByAuthor() {
        Assertions.assertEquals(Map.of("admin", 3L, "user1", 4L, "user2", 2L),
                statisticsService.getStatisticsByAuthor());
    }

    @Test
    @Order(75)
    @DisplayName("getStatisticsBetweenDate -> Проверка метода на работоспособность")
    void checkCorrectOutputStatisticsTest9() {
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(Map.of(now, 9L, now.minusDays(1), 0L, now.minusDays(2), 0L),
                statisticsService.getStatisticsBetweenDate(now, now.minusDays(2)));
    }

    @Test
    @Order(80)
    @DisplayName("-> Создание роли")
    void createRole() {
        Assertions.assertEquals(ResponseEntity.ok("Role successfully created!"),
                roleService.createRole(new Role("ACCOUNTANT")));
    }

    @Test
    @Order(85)
    @DisplayName("-> Попытка создать существующую роль")
    void createRoleShouldThrowException() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> roleService.createRole(new Role("ACCOUNTANT")));
    }

    @Test
    @Order(85)
    @DisplayName("-> Получаем роль которую только что создали по имени")
    void getRoleByName() {
        Assertions.assertTrue(roleService.getRoleByName("ACCOUNTANT").isPresent());
    }

    @Test
    @Order(85)
    @DisplayName("-> Получаем сет всех ролей")
    void getRoles() {
        Assertions.assertEquals(Set.of("ADMIN", "USER", "ACCOUNTANT"),
                roleService.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
    }

}
