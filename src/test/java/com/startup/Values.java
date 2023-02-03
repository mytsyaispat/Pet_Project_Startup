package com.startup;

import com.startup.entity.Article;
import com.startup.entity.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Values {

    static final Category[] categoryArrayForStatistics = new Category[]{
            new Category(1L, "Category1"),
            new Category(2L, "Category2"),
            new Category(3L, "Category3"),
            new Category(4L, "Category4")
    };

    static final Article[] correctArticles = new Article[]{
            new Article("title", "author", "content", LocalDateTime.now(), categoryArrayForStatistics[0]),
            new Article("titleTitleTitle", "authorAuthorAuthor", "contentContentContent", LocalDateTime.now().minusDays(2), categoryArrayForStatistics[0]),
            new Article("    Hello ", " Wo__+r-l-d-", "-!-", LocalDateTime.now().minusDays(3), categoryArrayForStatistics[1]),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(10), categoryArrayForStatistics[2]),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(7), categoryArrayForStatistics[2]),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(6), categoryArrayForStatistics[3])
    };

    static final Article[] incorrectArticles = new Article[]{
            new Article("", "author", "content", LocalDateTime.now().minusMinutes(5), categoryArrayForStatistics[0]),
            new Article("Hello", "author", "", LocalDateTime.now().minusMinutes(15), categoryArrayForStatistics[0]),
            new Article("CharactersCharactersCharactersCharactersCharactersCharactersCharactersCharactersCharactersCharacters103", "author", "content", LocalDateTime.now().minusMinutes(20), categoryArrayForStatistics[0])
    };

    static final Article[] correctArticlesForStatistics = new Article[]{
            new Article("title", "admin", "content", LocalDateTime.now(), categoryArrayForStatistics[0]),
            new Article("titleTitleTitle", "admin", "contentContentContent", LocalDateTime.now(), categoryArrayForStatistics[0]),
            new Article("    Hello ", "user2", "-!-", LocalDateTime.now().minusDays(1), categoryArrayForStatistics[0]),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(1), categoryArrayForStatistics[0]),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(1), categoryArrayForStatistics[0]),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(1), categoryArrayForStatistics[0]),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(1), categoryArrayForStatistics[0]),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(2), categoryArrayForStatistics[0]),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(2), categoryArrayForStatistics[0]),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(4), categoryArrayForStatistics[0]),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(4), categoryArrayForStatistics[0]),
            new Article("title", "user2", "author", LocalDateTime.now().minusDays(4), categoryArrayForStatistics[0]),
            new Article("title", "user2", "author", LocalDateTime.now().minusDays(4), categoryArrayForStatistics[0]),
            new Article("title", "user2", "author", LocalDateTime.now().minusDays(5), categoryArrayForStatistics[0]),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(5), categoryArrayForStatistics[0]),
            new Article("title", "user1", "author", LocalDateTime.now().minusDays(6), categoryArrayForStatistics[0]),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(6), categoryArrayForStatistics[0]),
            new Article("title", "author", "author", LocalDateTime.now().minusDays(6), categoryArrayForStatistics[0]),
            new Article("title", "user2", "author", LocalDateTime.now().minusDays(6), categoryArrayForStatistics[0])
    };

    static final List<Article> articleListForCategory1 = new ArrayList<>() {{
        add(correctArticlesForStatistics[0]);
        add(correctArticlesForStatistics[1]);
        add(correctArticlesForStatistics[2]);
        add(correctArticlesForStatistics[3]);
        add(correctArticlesForStatistics[4]);
    }};

    static final List<Article> articleListForCategory2 = new ArrayList<>() {{
        add(correctArticlesForStatistics[5]);
        add(correctArticlesForStatistics[6]);
        add(correctArticlesForStatistics[7]);
    }};

    static final List<Article> articleListForCategory3 = new ArrayList<>() {{
        add(correctArticlesForStatistics[8]);
        add(correctArticlesForStatistics[9]);
        add(correctArticlesForStatistics[10]);
        add(correctArticlesForStatistics[11]);
    }};

    static final List<Article> articleListForCategory4 = new ArrayList<>() {{
        add(correctArticlesForStatistics[12]);
        add(correctArticlesForStatistics[13]);
        add(correctArticlesForStatistics[14]);
        add(correctArticlesForStatistics[15]);
        add(correctArticlesForStatistics[16]);
        add(correctArticlesForStatistics[17]);
        add(correctArticlesForStatistics[18]);
    }};

}
