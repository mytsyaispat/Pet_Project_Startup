package com.startup;

import com.startup.auth.entity.Role;
import com.startup.auth.entity.User;
import com.startup.logic.controller.entity.ArticleRequest;
import com.startup.logic.entity.Article;
import com.startup.logic.entity.Category;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Values {

    public static final Role[] roleArray = new Role[]{
            new Role("ADMIN"),
            new Role("USER")
    };

    public static final Category[] categoryArray = new Category[]{
            new Category(1L, "Category1", List.of(), null),
            new Category(2L, "Category2", List.of(), null),
            new Category(3L, "Category3", List.of(), null),
            new Category(4L, "Category4", List.of(), null)
    };

    public static final User[] userArray = new User[]{
            new User("admin", "admin", Set.of(roleArray[0])),
            new User("user1", "user1", Set.of(roleArray[1])),
            new User("user2", "user2", Set.of(roleArray[1]))
    };

    public static final ArticleRequest[] incorrectArticlesForCreate = new ArticleRequest[]{
            new ArticleRequest("", "author", "Category1"),
            new ArticleRequest("Hello", "", "Category1"),
            new ArticleRequest("CharactersCharactersCharactersCharactersCharactersCharactersCharactersCharactersCharactersCharacters103", "content", "Category1")
    };

    public static final Article[] correctArticles = new Article[]{
            new Article("title", "content", LocalDateTime.now(), categoryArray[0], userArray[1]),
            new Article("titleTitleTitle", "contentContentContent", LocalDateTime.now().minusDays(2), categoryArray[2], userArray[2]),
            new Article("    Hello ", " Wo__+r-l-d--!-", LocalDateTime.now().minusDays(3), categoryArray[0], userArray[1]),
            new Article("title", "content", LocalDateTime.now().minusDays(10), categoryArray[2], userArray[1]),
            new Article("title", "content", LocalDateTime.now().minusDays(8), categoryArray[0], userArray[2]),
            new Article("title", "content", LocalDateTime.now().minusDays(9), categoryArray[3], userArray[1])
    };

    public static final Article[] correctArticlesForStatistics = new Article[]{
            new Article("title", "admin", LocalDateTime.now(), categoryArray[0], userArray[0]),
            new Article("titleTitleTitle", "admin", LocalDateTime.now(), categoryArray[0], userArray[0]),
            new Article("    Hello ", "user2", LocalDateTime.now().minusDays(1), categoryArray[0], userArray[0]),
            new Article("title", "author", LocalDateTime.now().minusDays(1), categoryArray[0], userArray[1]),
            new Article("title", "user1", LocalDateTime.now().minusDays(1), categoryArray[0], userArray[1]),
            new Article("title", "user1", LocalDateTime.now().minusDays(1), categoryArray[0], userArray[1]),
            new Article("title", "author", LocalDateTime.now().minusDays(1), categoryArray[1], userArray[1]),
            new Article("title", "user1", LocalDateTime.now().minusDays(2), categoryArray[1], userArray[1]),
            new Article("title", "author", LocalDateTime.now().minusDays(2), categoryArray[1], userArray[1]),
            new Article("title", "user1", LocalDateTime.now().minusDays(4), categoryArray[1], userArray[2]),
            new Article("title", "author", LocalDateTime.now().minusDays(4), categoryArray[1], userArray[2]),
            new Article("title", "user2", LocalDateTime.now().minusDays(4), categoryArray[1], userArray[2]),
            new Article("title", "user2", LocalDateTime.now().minusDays(4), categoryArray[1], userArray[2]),
            new Article("title", "user2", LocalDateTime.now().minusDays(5), categoryArray[2], userArray[2]),
            new Article("title", "user1", LocalDateTime.now().minusDays(5), categoryArray[2], userArray[2]),
            new Article("title", "user1", LocalDateTime.now().minusDays(6), categoryArray[2], userArray[2]),
            new Article("title", "author", LocalDateTime.now().minusDays(6), categoryArray[2], userArray[2]),
            new Article("title", "author", LocalDateTime.now().minusDays(6), categoryArray[2], userArray[2]),
            new Article("title", "user2", LocalDateTime.now().minusDays(6), categoryArray[3], userArray[2])
    };

}