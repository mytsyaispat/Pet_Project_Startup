package com.startup.service.impl;

import com.startup.entity.Article;
import com.startup.entity.Category;
import com.startup.service.ArticleService;
import com.startup.service.CategoryService;
import com.startup.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final ArticleService articleService;
    private final CategoryService categoryService;

    public StatisticsServiceImpl(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<Map<LocalDate, Long>> getStatisticsForTheLastWeek() {
        List<Article> articleList = articleService.findArticlesForTheLastSevenDays();
        if (articleList.isEmpty()) return ResponseEntity.ok(Collections.emptyMap());
        Map<LocalDate, Long> statistics = createStatisticsForTheLastWeek(articleList);
        return ResponseEntity.ok(statistics);
    }

    @Override
    public Map<LocalDate, Long> createStatisticsForTheLastWeek(List<Article> articleList) {
        return Stream.iterate(0, i -> i + 1)
                .limit(7)
                .collect(Collectors.toMap(i -> LocalDate.now().minusDays(i),
                        i -> articleList.stream()
                                .filter(article -> article.getDate().toLocalDate().equals(LocalDate.now().minusDays(i)))
                                .count()));
    }

    @Override
    public ResponseEntity<Map<String, Long>> getStatisticsByCategory() {
        List<Category> categoryList = categoryService.getCategoryList();
        if (categoryList.isEmpty()) return ResponseEntity.ok(Collections.emptyMap());
        Map<String, Long> statistics = createStatisticsByCategory(categoryList);
        return ResponseEntity.ok(statistics);
    }

    @Override
    public Map<String, Long> createStatisticsByCategory(List<Category> categoryList) {
        return categoryList.stream()
                .collect(Collectors.toMap(Category::getName,
                        category -> (long) articleService.getArticleListByCategory(category.getId()).size()));
    }

    @Override
    public ResponseEntity<Map<String, Long>> getStatisticsByAuthor() {
        List<Article> articleList = articleService.getArticleList();
        if (articleList.isEmpty()) return ResponseEntity.ok(Collections.emptyMap());
        Map<String, Long> statistics = createStatisticsByAuthor(articleList);
        return ResponseEntity.ok(statistics);
    }

    @Override
    public Map<String, Long> createStatisticsByAuthor(List<Article> articleList) {
        return articleList.stream()
                .collect(Collectors.groupingBy(Article::getAuthor, Collectors.counting()));
    }

    @Override
    public ResponseEntity<Map<LocalDate, Long>> getStatisticsBetweenDate(LocalDate firstDate, LocalDate secondDate) {
        if (firstDate.isAfter(secondDate)) {
            LocalDate swap = firstDate;
            firstDate = secondDate;
            secondDate = swap;
        }
        List<Article> articleList = articleService.getArticleList();
        Map<LocalDate, Long> body = new HashMap<>();
        for (; firstDate.isBefore(secondDate) || firstDate.isEqual(secondDate); firstDate = firstDate.plusDays(1)) {
            body.put(firstDate, getCountOfArticle(articleList, firstDate));
        }
        return ResponseEntity.ok(body);
    }

    private Long getCountOfArticle(List<Article> articleList, LocalDate firstDate) {
        return articleList.stream()
                .filter(article -> article.getDate().toLocalDate().isEqual(firstDate))
                .count();
    }

}
