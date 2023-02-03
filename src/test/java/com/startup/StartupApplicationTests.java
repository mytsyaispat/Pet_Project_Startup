package com.startup;

import com.startup.entity.Article;
import com.startup.entity.Category;
import com.startup.repository.ArticleRepository;
import com.startup.repository.CategoryRepository;
import com.startup.service.ArticleService;
import com.startup.service.CategoryService;
import com.startup.service.StatisticsService;
import com.startup.service.impl.ArticleServiceImpl;
import com.startup.service.impl.CategoryServiceImpl;
import com.startup.service.impl.StatisticsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class StartupApplicationTests {
    @Autowired
    ArticleRepository articleRepositoryAutowired;
    final ArticleRepository articleRepository = mock(ArticleRepository.class);
    final CategoryRepository categoryRepository = mock(CategoryRepository.class);
    final CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
    final ArticleService articleService = new ArticleServiceImpl(articleRepository, categoryService);
    final StatisticsService statisticsService = new StatisticsServiceImpl(articleService, categoryService);

    final Article[] correctArticles = new Article[]{
            new Article("title", "author", "content", LocalDateTime.now(), "Category1"),
            new Article("titleTitleTitle", "authorAuthorAuthor", "contentContentContent", LocalDateTime.now().minusDays(2), "Category1"),
            new Article("    Hello ", " Wo__+r-l-d-", "-!-", LocalDateTime.now().minusDays(3), "Category1"),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(10), "Category1"),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(7), "Category1"),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(6), "Category1")
    };

    final Article[] incorrectArticles = new Article[]{
            new Article("", "author", "content", LocalDateTime.now().minusMinutes(5), "Category1"),
            new Article("Hello", "author", "", LocalDateTime.now().minusMinutes(15), "Category1"),
            new Article("CharactersCharactersCharactersCharactersCharactersCharactersCharactersCharactersCharactersCharacters103", "author", "content", LocalDateTime.now().minusMinutes(20), "Category1")
    };

    final Article[] correctArticlesForStatistics = new Article[]{
            new Article("title", "admin", "content", LocalDateTime.now(), "Category1"),
            new Article("titleTitleTitle", "admin", "contentContentContent", LocalDateTime.now(), "Category1"),
            new Article("    Hello ", "user2", "-!-", LocalDateTime.now().minusDays(1), "Category1"),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(1), "Category1"),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(1), "Category1"),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(1), "Category1"),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(1), "Category1"),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(2), "Category1"),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(2), "Category1"),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(4), "Category1"),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(4), "Category1"),
            new Article("title", "user2", "author", LocalDateTime.now().minusDays(4), "Category1"),
            new Article("title", "user2", "author", LocalDateTime.now().minusDays(4), "Category1"),
            new Article("title", "user2", "author", LocalDateTime.now().minusDays(5), "Category1"),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(5), "Category1"),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(6), "Category1"),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(6), "Category1"),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(6), "Category1"),
            new Article("title", "user2", "author", LocalDateTime.now().minusDays(6), "Category1")
    };

    final List<Article> articleListForCategory1 = new ArrayList<>() {{
        add(correctArticlesForStatistics[0]);
        add(correctArticlesForStatistics[1]);
        add(correctArticlesForStatistics[2]);
        add(correctArticlesForStatistics[3]);
        add(correctArticlesForStatistics[4]);
    }};

    final List<Article> articleListForCategory2 = new ArrayList<>() {{
        add(correctArticlesForStatistics[5]);
        add(correctArticlesForStatistics[6]);
        add(correctArticlesForStatistics[7]);
    }};

    final List<Article> articleListForCategory3 = new ArrayList<>() {{
        add(correctArticlesForStatistics[8]);
        add(correctArticlesForStatistics[9]);
        add(correctArticlesForStatistics[10]);
        add(correctArticlesForStatistics[11]);
    }};

    final List<Article> articleListForCategory4 = new ArrayList<>() {{
        add(correctArticlesForStatistics[12]);
        add(correctArticlesForStatistics[13]);
        add(correctArticlesForStatistics[14]);
        add(correctArticlesForStatistics[15]);
        add(correctArticlesForStatistics[16]);
        add(correctArticlesForStatistics[17]);
        add(correctArticlesForStatistics[18]);
    }};

    final Category[] categoryArrayForStatistics = new Category[]{
            new Category("Category1", articleListForCategory1),
            new Category("Category2", articleListForCategory2),
            new Category("Category3", articleListForCategory3),
            new Category("Category4", articleListForCategory4)
    };

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    @DisplayName("test1-6 addCorrectArticles1")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void addCorrectArticles1(int counter) {
        articleRepositoryAutowired.deleteAll();
        articleRepositoryAutowired.save(correctArticles[counter]);
        List<Article> articleList = articleRepositoryAutowired.findAll();
        Assertions.assertEquals(List.of(correctArticles[counter]).toString(), articleList.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    @DisplayName("test7-12 addCorrectArticles2")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void addCorrectArticles2(int counter) {
        articleRepositoryAutowired.deleteAll();
        articleRepositoryAutowired.save(correctArticles[counter]);
        List<Article> articleList = articleRepositoryAutowired.findAll();
        Assertions.assertNotEquals(List.of(correctArticles[counter + 1]).toString(), articleList.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    @DisplayName("test13-15 tryToAddIncorrectArticles")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void tryToSaveIncorrectArticles(int counter) {
        articleRepositoryAutowired.deleteAll();
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            articleRepositoryAutowired.save(incorrectArticles[counter]);
            articleRepositoryAutowired.findAll();
        });
    }

    @Test
    @DisplayName("test16 checkValidationArticleList")
    void checkValidationArticleList() {
        for (Article article : correctArticles) {
            articleService.createArticle(article);
        }
        Mockito.when(articleService.findArticlesForTheLastSevenDays()).thenReturn(List.of(correctArticles[0], correctArticles[1], correctArticles[2]));
    }

    @Test
    @DisplayName("test17 checkStatisticsMethodForTheLastWeek1")
    void checkStatisticsMethodForTheLastWeek1() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(correctArticles));
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 1L,
                now.minusDays(1), 0L, now.minusDays(2), 1L,
                now.minusDays(3), 1L, now.minusDays(4), 0L,
                now.minusDays(5), 0L, now.minusDays(6), 1L));
    }

    @Test
    @DisplayName("test18 checkStatisticsMethodForTheLastWeek2")
    void checkStatisticsMethodForTheLastWeek2() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(correctArticles));
        LocalDate now = LocalDate.now();
        Assertions.assertNotEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 4L,
                now.minusDays(1), 3L, now.minusDays(2), 6L,
                now.minusDays(3), 5L, now.minusDays(4), 0L,
                now.minusDays(5), 1L, now.minusDays(6), 8L));
    }

    @Test
    @DisplayName("test19 checkStatisticsMethodForTheLastWeek3")
    void checkStatisticsMethodForTheLastWeek3() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(correctArticlesForStatistics));
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 2L,
                now.minusDays(1), 5L, now.minusDays(2), 2L,
                now.minusDays(3), 0L, now.minusDays(4), 4L,
                now.minusDays(5), 2L, now.minusDays(6), 4L));
    }

    @Test
    @DisplayName("test20 checkStatisticsMethodForTheLastWeek4")
    void checkStatisticsMethodForTheLastWeek4() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(correctArticlesForStatistics));
        LocalDate now = LocalDate.now();
        Assertions.assertNotEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 0L,
                now.minusDays(1), 2L, now.minusDays(2), 1L,
                now.minusDays(3), 7L, now.minusDays(4), 1L,
                now.minusDays(5), 5L, now.minusDays(6), 0L));
    }

    @Test
    @DisplayName("test21 checkStatisticsMethodByCategory1")
    void checkStatisticsMethodByCategory1() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(categoryArrayForStatistics));
        Assertions.assertEquals(statisticsService.createStatisticsByCategory(categoryList),
                Map.of("Category1", 5L, "Category2", 3L, "Category3", 4L, "Category4", 7L));
    }

    @Test
    @DisplayName("test22 checkStatisticsMethodByCategory2")
    void checkStatisticsMethodByCategory2() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(categoryArrayForStatistics));
        Assertions.assertNotEquals(statisticsService.createStatisticsByCategory(categoryList),
                Map.of("Category1", 2L, "Category2", 0L, "Category3", 9L, "Category4", 6L));
    }

    @Test
    @DisplayName("test23 checkStatisticsMethodByAuthor1")
    void checkStatisticsMethodByAuthor1() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(correctArticlesForStatistics));
        Assertions.assertEquals(statisticsService.createStatisticsByAuthor(articleList),
                Map.of("admin", 2L, "user1", 6L, "user2", 5L, "author", 6L));
    }

    @Test
    @DisplayName("test24 checkStatisticsMethodByAuthor2")
    void checkStatisticsMethodByAuthor2() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(correctArticlesForStatistics));
        Assertions.assertNotEquals(statisticsService.createStatisticsByAuthor(articleList),
                Map.of("admin", 0L, "user1", 0L, "user2", 0L, "author", 0L));
    }
}
