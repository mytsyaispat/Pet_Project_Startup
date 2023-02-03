package com.startup.controller.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class ArticleRequest {

    @NotBlank(message = "Title must not be empty!")
    @Size(max = 100, message = "Title should not exceed 100 characters!")
    private final String title;
    @NotBlank(message = "Content must not be empty!")
    private final String content;
    @NotBlank(message = "Category must not be empty!")
    private final String category;

    public ArticleRequest(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }
}
