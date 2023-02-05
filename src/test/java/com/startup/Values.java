package com.startup;

import com.startup.auth.entity.Role;
import com.startup.auth.entity.User;
import com.startup.logic.controller.entity.ArticleRequest;
import com.startup.logic.entity.Article;
import com.startup.logic.entity.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Values {

    public static final Role[] roleArray = new Role[]{
            new Role("ADMIN"),
            new Role("USER")
    };

    public static final Category[] categoryArray = new Category[]{
            new Category(1L, "Category1"),
            new Category(2L, "Category2"),
            new Category(3L, "Category3"),
            new Category(4L, "Category4")
    };

    public static final User[] userArray = new User[]{
            new User("admin", "admin", Set.of(roleArray[0])),
            new User("user1", "user1", Set.of(roleArray[1])),
            new User("user2", "user2", Set.of(roleArray[1]))
    };

    public static final ArticleRequest[] articlesForCreate = new ArticleRequest[] {
            new ArticleRequest("title", "content", "Category1"),
            new ArticleRequest("title", "content", "Category2"),
            new ArticleRequest("title", "content", "Category3"),
    };

    public static final Article[] correctArticles = new Article[]{
            new Article("title", "content", LocalDateTime.now(), categoryArray[0], userArray[1]),
            new Article("titleTitleTitle", "contentContentContent", LocalDateTime.now().minusDays(2), categoryArray[2], userArray[2]),
            new Article("    Hello ", " Wo__+r-l-d--!-", LocalDateTime.now().minusDays(3), categoryArray[0], userArray[1]),
            new Article("title", "content", LocalDateTime.now().minusDays(10), categoryArray[2], userArray[1]),
            new Article("title", "content", LocalDateTime.now().minusDays(8), categoryArray[0], userArray[2]),
            new Article("title", "content", LocalDateTime.now().minusDays(9), categoryArray[3], userArray[1])
    };

    public static final Article[] incorrectArticles = new Article[]{
            new Article("", "author", LocalDateTime.now().minusMinutes(5), categoryArray[2], userArray[1]),
            new Article("Hello", "", LocalDateTime.now().minusMinutes(15), categoryArray[1], userArray[1]),
            new Article("CharactersCharactersCharactersCharactersCharactersCharactersCharactersCharactersCharactersCharacters103", "content", LocalDateTime.now().minusMinutes(20), categoryArray[0], userArray[2])
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

    public static final List<Article> articleListForCategory1 = new ArrayList<>() {{
        add(correctArticlesForStatistics[0]);
        add(correctArticlesForStatistics[1]);
        add(correctArticlesForStatistics[2]);
        add(correctArticlesForStatistics[3]);
        add(correctArticlesForStatistics[4]);
    }};

    public static final List<Article> articleListForCategory2 = new ArrayList<>() {{
        add(correctArticlesForStatistics[5]);
        add(correctArticlesForStatistics[6]);
        add(correctArticlesForStatistics[7]);
    }};

    public static final List<Article> articleListForCategory3 = new ArrayList<>() {{
        add(correctArticlesForStatistics[8]);
        add(correctArticlesForStatistics[9]);
        add(correctArticlesForStatistics[10]);
        add(correctArticlesForStatistics[11]);
    }};

    public static final List<Article> articleListForCategory4 = new ArrayList<>() {{
        add(correctArticlesForStatistics[12]);
        add(correctArticlesForStatistics[13]);
        add(correctArticlesForStatistics[14]);
        add(correctArticlesForStatistics[15]);
        add(correctArticlesForStatistics[16]);
        add(correctArticlesForStatistics[17]);
        add(correctArticlesForStatistics[18]);
    }};



}
