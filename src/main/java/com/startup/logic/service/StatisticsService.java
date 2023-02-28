package com.startup.logic.service;

import java.time.LocalDate;
import java.util.Map;

public interface StatisticsService {

    Map<String, Long> getStatisticsByCategory();

    Map<String, Long> getStatisticsByAuthor();

    Map<LocalDate, Long> getStatisticsBetweenDate(LocalDate firstDate, LocalDate secondDate);


    Map<LocalDate, Long> getStatisticsForTheLastWeek();
}
