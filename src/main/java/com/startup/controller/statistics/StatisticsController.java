package com.startup.controller.statistics;

import com.startup.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("startup/admin/statistics/")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("week")
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
}
