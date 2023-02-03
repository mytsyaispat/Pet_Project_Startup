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
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UnitTests {

    ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);
    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
    ArticleService articleService = new ArticleServiceImpl(articleRepository, categoryService);
    StatisticsService statisticsService = new StatisticsServiceImpl(articleService, categoryService);

    @Test
    @DisplayName("test16 checkStatisticsMethodForTheLastWeek1")
    void checkStatisticsMethodForTheLastWeek1() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticles));
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 1L,
                now.minusDays(1), 0L, now.minusDays(2), 1L,
                now.minusDays(3), 1L, now.minusDays(4), 0L,
                now.minusDays(5), 0L, now.minusDays(6), 1L));
    }

    @Test
    @DisplayName("test17 checkStatisticsMethodForTheLastWeek2")
    void checkStatisticsMethodForTheLastWeek2() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticles));
        LocalDate now = LocalDate.now();
        Assertions.assertNotEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 4L,
                now.minusDays(1), 3L, now.minusDays(2), 6L,
                now.minusDays(3), 5L, now.minusDays(4), 0L,
                now.minusDays(5), 1L, now.minusDays(6), 8L));
    }

    @Test
    @DisplayName("test18 checkStatisticsMethodForTheLastWeek3")
    void checkStatisticsMethodForTheLastWeek3() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticlesForStatistics));
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 2L,
                now.minusDays(1), 5L, now.minusDays(2), 2L,
                now.minusDays(3), 0L, now.minusDays(4), 4L,
                now.minusDays(5), 2L, now.minusDays(6), 4L));
    }

    @Test
    @DisplayName("test19 checkStatisticsMethodForTheLastWeek4")
    void checkStatisticsMethodForTheLastWeek4() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticlesForStatistics));
        LocalDate now = LocalDate.now();
        Assertions.assertNotEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 0L,
                now.minusDays(1), 2L, now.minusDays(2), 1L,
                now.minusDays(3), 7L, now.minusDays(4), 1L,
                now.minusDays(5), 5L, now.minusDays(6), 0L));
    }

    @Test
    @DisplayName("test21 checkStatisticsMethodByCategory2")
    void checkStatisticsMethodByCategory2() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(Values.categoryArrayForStatistics));
        Assertions.assertNotEquals(statisticsService.createStatisticsByCategory(categoryList),
                Map.of("Category1", 2L, "Category2", 0L, "Category3", 9L, "Category4", 6L));
    }

    @Test
    @DisplayName("test22 checkStatisticsMethodByAuthor1")
    void checkStatisticsMethodByAuthor1() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticlesForStatistics));
        Assertions.assertEquals(statisticsService.createStatisticsByAuthor(articleList),
                Map.of("admin", 2L, "user1", 6L, "user2", 5L, "author", 6L));
    }

    @Test
    @DisplayName("test23 checkStatisticsMethodByAuthor2")
    void checkStatisticsMethodByAuthor2() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticlesForStatistics));
        Assertions.assertNotEquals(statisticsService.createStatisticsByAuthor(articleList),
                Map.of("admin", 0L, "user1", 0L, "user2", 0L, "author", 0L));
    }

}
