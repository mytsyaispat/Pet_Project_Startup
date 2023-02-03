package com.startup.service;

import com.startup.entity.Article;
import com.startup.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StatisticsService {

    ResponseEntity<Map<LocalDate, Long>> getStatisticsForTheLastWeek();
    Map<LocalDate, Long> createStatisticsForTheLastWeek(List<Article> articleList);
    ResponseEntity<Map<String, Long>> getStatisticsByCategory();
    Map<String, Long> createStatisticsByCategory(List<Category> categoryList);
    ResponseEntity<Map<String, Long>> getStatisticsByAuthor();
    Map<String, Long> createStatisticsByAuthor(List<Article> articleList);

    ResponseEntity<Map<LocalDate, Long>> getStatisticsBetweenDate(LocalDate firstDate, LocalDate secondDate);


}
