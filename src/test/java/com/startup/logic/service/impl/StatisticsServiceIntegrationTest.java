package com.startup.logic.service.impl;

import com.startup.Values;
import com.startup.logic.entity.Article;
import com.startup.logic.entity.Category;
import com.startup.logic.service.StatisticsService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Проверка на правильность работы методов StatisticsService
 */

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StatisticsServiceIntegrationTest {
    @Autowired
    StatisticsService statisticsService;

    @Test
    @DisplayName("test1 -> createStatisticsForTheLastWeek method")
    void checkCorrectOutputStatisticsTest1() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticles));
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(statisticsService.getStatisticsForTheLastWeek(articleList), Map.of(now, 1L,
                now.minusDays(1), 0L, now.minusDays(2), 1L,
                now.minusDays(3), 1L, now.minusDays(4), 0L,
                now.minusDays(5), 0L, now.minusDays(6), 0L));
    }

    @Test
    @DisplayName("test2 -> createStatisticsForTheLastWeek method")
    void checkCorrectOutputStatisticsTest2() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticles));
        LocalDate now = LocalDate.now();
        Assertions.assertNotEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 4L,
                now.minusDays(1), 3L, now.minusDays(2), 6L,
                now.minusDays(3), 5L, now.minusDays(4), 0L,
                now.minusDays(5), 1L, now.minusDays(6), 8L));
    }

    @Test
    @DisplayName("test3 -> createStatisticsForTheLastWeek method")
    void checkCorrectOutputStatisticsTest3() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticlesForStatistics));
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 2L,
                now.minusDays(1), 5L, now.minusDays(2), 2L,
                now.minusDays(3), 0L, now.minusDays(4), 4L,
                now.minusDays(5), 2L, now.minusDays(6), 4L));
    }

    @Test
    @DisplayName("test4 -> createStatisticsForTheLastWeek method")
    void checkCorrectOutputStatisticsTest4() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticlesForStatistics));
        LocalDate now = LocalDate.now();
        Assertions.assertNotEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 0L,
                now.minusDays(1), 2L, now.minusDays(2), 1L,
                now.minusDays(3), 7L, now.minusDays(4), 1L,
                now.minusDays(5), 5L, now.minusDays(6), 0L));
    }

    @Test
    @DisplayName("test5 -> createStatisticsByCategory method")
    void checkCorrectOutputStatisticsTest5() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(Values.categoryArray));
        Assertions.assertEquals(statisticsService.createStatisticsByCategory(categoryList, Arrays.asList(Values.correctArticlesForStatistics)),
                Map.of("Category1", 6L, "Category2", 7L, "Category3", 5L, "Category4", 1L));
    }

    @Test
    @DisplayName("test6 -> createStatisticsByCategory method")
    void checkCorrectOutputStatisticsTest6() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(Values.categoryArray));
        Assertions.assertNotEquals(statisticsService.createStatisticsByCategory(categoryList, Arrays.asList(Values.correctArticlesForStatistics)),
                Map.of("Category1", 2L, "Category2", 0L, "Category3", 9L, "Category4", 6L));
    }

    @Test
    @DisplayName("test7 -> createStatisticsByAuthor method")
    void checkCorrectOutputStatisticsTest7() {
        Assertions.assertEquals(statisticsService.createStatisticsByAuthor(Arrays.asList(Values.correctArticlesForStatistics)),
                Map.of("admin", 3L, "user1", 6L, "user2", 10L));
    }

    @Test
    @DisplayName("test8 -> createStatisticsByAuthor method")
    void checkCorrectOutputStatisticsTest8() {
        Assertions.assertNotEquals(statisticsService.createStatisticsByAuthor(Arrays.asList(Values.correctArticlesForStatistics)),
                Map.of("admin", 4L, "user1", 2L, "user2", 0L));
    }

    @Test
    @DisplayName("test9 -> createStatisticsByDatesBetween method")
    void checkCorrectOutputStatisticsTest9() {
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(statisticsService.createStatisticsByDatesBetween(now, now.minusDays(6), Arrays.asList(Values.correctArticlesForStatistics)), Map.of(now, 2L,
                now.minusDays(1), 5L, now.minusDays(2), 2L,
                now.minusDays(3), 0L, now.minusDays(4), 4L,
                now.minusDays(5), 2L, now.minusDays(6), 4L));
    }

    @Test
    @DisplayName("test10 -> createStatisticsByDatesBetween method")
    void checkCorrectOutputStatisticsTest10() {
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(statisticsService.createStatisticsByDatesBetween(now, now.minusDays(9), Arrays.asList(Values.correctArticlesForStatistics)),
                Map.of(now, 2L, now.minusDays(1), 5L, now.minusDays(2), 2L,
                        now.minusDays(3), 0L, now.minusDays(4), 4L,
                        now.minusDays(5), 2L, now.minusDays(6), 4L,
                        now.minusDays(7), 0L, now.minusDays(8), 0L,
                        now.minusDays(9), 0L));
    }


}
