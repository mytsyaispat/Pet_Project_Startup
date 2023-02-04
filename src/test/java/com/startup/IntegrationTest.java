package com.startup;

import com.startup.Logic.entity.Article;
import com.startup.Logic.entity.Category;
import com.startup.Logic.repository.ArticleRepository;
import com.startup.Logic.repository.CategoryRepository;
import com.startup.Logic.service.ArticleService;
import com.startup.Logic.service.CategoryService;
import com.startup.Logic.service.StatisticsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class IntegrationTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private StatisticsService statisticsService;

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    @DisplayName("test1-6 addCorrectArticles1")
    void addCorrectArticles1(int counter) {
        articleRepository.deleteAll();
        articleRepository.save(Values.correctArticles[counter]);
        List<Article> articleList = articleRepository.findAll();
        Assertions.assertEquals(List.of(Values.correctArticles[counter]).toString(), articleList.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    @DisplayName("test7-12 addCorrectArticles2")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void addCorrectArticles2(int counter) {
        articleRepository.deleteAll();
        articleRepository.save(Values.correctArticles[counter]);
        List<Article> articleList = articleRepository.findAll();
        Assertions.assertNotEquals(List.of(Values.correctArticles[counter + 1]).toString(), articleList.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    @DisplayName("test13-15 tryToAddIncorrectArticles")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void tryToSaveIncorrectArticles(int counter) {
        articleRepository.deleteAll();
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            articleRepository.save(Values.incorrectArticles[counter]);
            articleRepository.findAll();
        });
    }

    @Test
    @DisplayName("test20 checkStatisticsMethodByCategory1")
    void checkStatisticsMethodByCategory1() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(Values.categoryArrayForStatistics));
        Assertions.assertEquals(statisticsService.createStatisticsByCategory(categoryList),
                Map.of("Category1", 5L, "Category2", 3L, "Category3", 4L, "Category4", 7L));
    }

    @Test
    @DisplayName("test21 checkStatisticsMethodByCategory2")
    void checkStatisticsMethodByCategory2() {
        List<Category> categoryList = new ArrayList<>(Arrays.asList(Values.categoryArrayForStatistics));
        Assertions.assertNotEquals(statisticsService.createStatisticsByCategory(categoryList),
                Map.of("Category1", 2L, "Category2", 0L, "Category3", 9L, "Category4", 6L));
    }

}
