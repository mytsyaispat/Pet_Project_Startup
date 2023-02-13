package com.startup.logic.service.impl;

import com.startup.logic.controller.entity.CategoryLink;
import com.startup.logic.entity.Category;
import com.startup.logic.service.CategoryService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceIntegrationTest {
    @Autowired
    CategoryService categoryService;

    @Test
    @Order(5)
    @DisplayName(value = "Test№1 -> Создание нескольких категорий и проверка на добавление в базу")
    void createCategory1() {
        categoryService.createCategory(new Category("Машины"));
        categoryService.createCategory(new Category("Внедорожники"));
        categoryService.createCategory(new Category("Легковые машины"));
        List<Category> categoryList = categoryService.getCategoryList();
        Assertions.assertEquals(3, categoryList.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Машины", "Внедорожники", "Легковые машины"})
    @Order(10)
    @DisplayName(value = "Test№2-4 -> Проверка добавленных категорий, выборка по имени")
    void getCategoryByName(String categoryName) {
        Optional<Category> categoryOptional = categoryService.getCategoryByName(categoryName);
        Assertions.assertTrue(categoryOptional.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Машины", "Внедорожники", "Легковые машины"})
    @Order(13)
    @DisplayName(value = "Test№5-7 -> Попытка добавить существующую категорию")
    void createCategoryShouldThrowException(String categoryName) {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategory(new Category(categoryName)));
    }

    @Test
    @Order(15)
    @DisplayName(value = "Test№8 -> Попытка наследования от самой себя")
    void createCategoryLinkShouldThrowException1() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink("Машины", "Машины")));
    }

    @Test
    @Order(20)
    @DisplayName(value = "Test№9 -> Указание несуществующей parent категории")
    void createCategoryLinkShouldThrowException2() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink("Чебупель", "Легковые машины")));
    }

    @Test
    @Order(25)
    @DisplayName(value = "Test№10 -> Указание несуществующей child категории")
    void createCategoryLinkShouldThrowException3() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink("Машины", "Фрикадельки")));
    }

    @ParameterizedTest
    @CsvSource({"Машины,Легковые машины", "Машины,Внедорожники"})
    @Order(30)
    @DisplayName(value = "Test№11-12 -> Корректное добавление связей между категориями")
    void createCategoryLink(String parent, String child) {
        Assertions.assertEquals(ResponseEntity.ok("Link successfully create!"),
                categoryService.createCategoryLink(new CategoryLink(parent, child)));
    }

    @ParameterizedTest
    @CsvSource({"Машины,Легковые машины", "Машины,Внедорожники"})
    @Order(35)
    @DisplayName(value = "Test№13-14 -> Попытка добавить существующую связь")
    void createCategoryLinkShouldThrowException4(String parent, String child) {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink(parent, child)));
    }

    @ParameterizedTest
    @CsvSource({"Легковые машины,Внедорожники", "Внедорожники,Легковые машины"})
    @Order(40)
    @DisplayName(value = "Test№15-16 -> Попытка использовать категорию child в качестве child у другой категории")
    void createCategoryLinkShouldThrowException5(String parent, String child) {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink(parent, child)));
    }

    @ParameterizedTest
    @CsvSource({"Легковые машины,Машины", "Внедорожники,Машины"})
    @Order(45)
    @DisplayName(value = "Test№17-18 -> Попытка использовать категорию parent в качестве child у своих же child")
    void createCategoryLinkShouldThrowException6(String parent, String child) {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> categoryService.createCategoryLink(new CategoryLink(parent, child)));
    }

    @Test
    @Order(50)
    @DisplayName(value = "Test№19 -> Проверка метода на возвращение категорий у которых нет parent")
    void getCategoryListWhereParentIdIsNull() {
        addSomeCorrectCategories();
        addSomeCorrectLinks();
        Assertions.assertEquals(3, categoryService.getCategoryListWhereParentIdIsNull().size());
    }

    void addSomeCorrectCategories() {
        categoryService.createCategory(new Category("Легковая машина №1"));
        categoryService.createCategory(new Category("Легковая машина №2"));
        categoryService.createCategory(new Category("Легковая машина №3"));
        categoryService.createCategory(new Category("Легковая машина №4"));
        categoryService.createCategory(new Category("Внедорожная машина №1"));
        categoryService.createCategory(new Category("Внедорожная машина №2"));
        categoryService.createCategory(new Category("Внедорожная машина №3"));
        categoryService.createCategory(new Category("Еда"));
        categoryService.createCategory(new Category("Random category 1"));
        categoryService.createCategory(new Category("Random category 2"));
        categoryService.createCategory(new Category("Random category 3"));
    }

    void addSomeCorrectLinks() {
        categoryService.createCategoryLink(new CategoryLink("Легковые машины", "Легковая машина №1"));
        categoryService.createCategoryLink(new CategoryLink("Легковые машины", "Легковая машина №2"));
        categoryService.createCategoryLink(new CategoryLink("Легковые машины", "Легковая машина №3"));
        categoryService.createCategoryLink(new CategoryLink("Легковые машины", "Легковая машина №4"));
        categoryService.createCategoryLink(new CategoryLink("Внедорожники", "Внедорожная машина №1"));
        categoryService.createCategoryLink(new CategoryLink("Внедорожники", "Внедорожная машина №2"));
        categoryService.createCategoryLink(new CategoryLink("Внедорожники", "Внедорожная машина №3"));
        categoryService.createCategoryLink(new CategoryLink("Random category 1", "Random category 2"));
        categoryService.createCategoryLink(new CategoryLink("Random category 1", "Random category 3"));
    }

}
