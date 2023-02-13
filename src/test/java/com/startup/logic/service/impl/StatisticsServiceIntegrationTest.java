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



}
