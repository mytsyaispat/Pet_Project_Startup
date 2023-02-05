package com.startup.logic.controller;

import com.startup.logic.controller.entity.DateRequest;

import com.startup.logic.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("admin/statistics/")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("date/week")
    public ResponseEntity<Map<LocalDate, Long>> getStatisticsForTheLastWeek() {
        return statisticsService.getStatisticsForTheLastWeek();
    }

    @GetMapping("category")
    public ResponseEntity<Map<String, Long>> getStatisticsByCategory() {
        return statisticsService.getStatisticsByCategory();
    }

    @GetMapping("author")
    public ResponseEntity<Map<String, Long>> getStatisticsByAuthor() {
        return statisticsService.getStatisticsByAuthor();
    }

    @GetMapping("date/between")
    public ResponseEntity<Map<LocalDate, Long>> getStatisticsBetweenDate(@RequestBody DateRequest dateRequest) {
        return statisticsService.getStatisticsBetweenDate(dateRequest.getFirstDate(), dateRequest.getSecondDate());
    }
}
