package com.startup.controller.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ArticleRequest {

    @NotBlank(message = "Title must not be empty!")
    @Size(max = 100, message = "Title should not exceed 100 characters!")
    private String title;
    @NotBlank(message = "Content must not be empty!")
    private String content;
    @NotBlank(message = "Category must not be empty!")
    private String category;

    public ArticleRequest(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public ArticleRequest() {}

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
