package com.startup;

import com.startup.auth.repository.RoleRepository;
import com.startup.auth.repository.UserRepository;
import com.startup.auth.service.RoleService;
import com.startup.auth.service.UserService;
import com.startup.auth.service.impl.RoleServiceImpl;
import com.startup.auth.service.impl.UserServiceImpl;
import com.startup.logic.entity.Article;
import com.startup.logic.entity.Category;
import com.startup.logic.repository.ArticleRepository;
import com.startup.logic.repository.CategoryRepository;
import com.startup.logic.service.ArticleService;
import com.startup.logic.service.CategoryService;
import com.startup.logic.service.StatisticsService;
import com.startup.logic.service.impl.ArticleServiceImpl;
import com.startup.logic.service.impl.CategoryServiceImpl;
import com.startup.logic.service.impl.StatisticsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UnitTests {

    ArticleRepository articleRepository = Mockito.mock(ArticleRepository.class);
    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
    CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
    RoleService roleService = new RoleServiceImpl(roleRepository);
    UserService userService = new UserServiceImpl(passwordEncoder, userRepository, roleService);
    ArticleService articleService = new ArticleServiceImpl(articleRepository, categoryService, userService);
    StatisticsService statisticsService = new StatisticsServiceImpl(articleService, categoryService);

    @Test
    @DisplayName("test16 checkStatisticsMethodForTheLastWeek1")
    void checkStatisticsMethodForTheLastWeek1() {
        List<Article> articleList = new ArrayList<>(Arrays.asList(Values.correctArticles));
        LocalDate now = LocalDate.now();
        Assertions.assertEquals(statisticsService.createStatisticsForTheLastWeek(articleList), Map.of(now, 1L,
                now.minusDays(1), 0L, now.minusDays(2), 1L,
                now.minusDays(3), 1L, now.minusDays(4), 0L,
                now.minusDays(5), 0L, now.minusDays(6), 0L));
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
    @DisplayName("test20 checkStatisticsMethodByCategory1")
    void checkStatisticsMethodByCategory1() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(Values.categoryArray));
        Assertions.assertEquals(statisticsService.createStatisticsByCategory(categoryList, Arrays.asList(Values.correctArticlesForStatistics)),
                Map.of("Category1", 6L, "Category2", 7L, "Category3", 5L, "Category4", 1L));
    }

    @Test
    @DisplayName("test21 checkStatisticsMethodByCategory2")
    void checkStatisticsMethodByCategory2() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(Values.categoryArray));
        Assertions.assertNotEquals(statisticsService.createStatisticsByCategory(categoryList, Arrays.asList(Values.correctArticlesForStatistics)),
                Map.of("Category1", 2L, "Category2", 0L, "Category3", 9L, "Category4", 6L));
    }

    @Test
    @DisplayName("test22 checkStatisticsMethodByAuthor1")
    void checkStatisticsMethodByAuthor1() {
        Assertions.assertEquals(statisticsService.createStatisticsByAuthor(Arrays.asList(Values.correctArticlesForStatistics)),
                Map.of("admin", 3L, "user1", 6L, "user2", 10L));
    }

    @Test
    @DisplayName("test23 checkStatisticsMethodByAuthor2")
    void checkStatisticsMethodByAuthor2() {
        List<Article> articleList = new ArrayList<>();
        Assertions.assertNotEquals(statisticsService.createStatisticsByAuthor(Arrays.asList(Values.correctArticlesForStatistics)),
                Map.of("admin", 4L, "user1", 2L, "user2", 0L));
    }



}
