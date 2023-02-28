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

    @GetMapping("category")
    public ResponseEntity<Map<String, Long>> getStatisticsByCategory() {
        Map<String, Long> body = statisticsService.getStatisticsByCategory();
        return ResponseEntity.ok(body);
    }

    @GetMapping("author")
    public ResponseEntity<Map<String, Long>> getStatisticsByAuthor() {
        Map<String, Long> body = statisticsService.getStatisticsByAuthor();
        return ResponseEntity.ok(body);
    }

    @GetMapping("date")
    public ResponseEntity<Map<LocalDate, Long>> getStatisticsBetweenDate(@RequestBody DateRequest dateRequest) {
        Map<LocalDate, Long> body = statisticsService.getStatisticsBetweenDate(dateRequest.getFirstDate(), dateRequest.getSecondDate());
        return ResponseEntity.ok(body);
    }

    @GetMapping("week")
    public ResponseEntity<Map<LocalDate, Long>> getStatisticsForTheLastWeek() {
        Map<LocalDate, Long> body = statisticsService.getStatisticsForTheLastWeek();
        return ResponseEntity.ok(body);
    }

}
