package com.startup.logic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.startup.auth.entity.User;
import com.startup.logic.controller.entity.ArticleRequest;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "article")
@JsonPropertyOrder({
        "title",
        "author",
        "content",
        "date"
})
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 100)
    @NotBlank(message = "Title must not be empty!")
    @Size(max = 100, message = "Title should not exceed 100 characters!")
    private String title;
    @Column(nullable = false)
    @NotBlank(message = "Content must not be empty!")
    private String content;
    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Article() {
    }

    public Article(String title, String content, LocalDateTime date, Category category, User user) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.category = category;
        this.user = user;
    }

    public Article(ArticleRequest articleRequest, Category category, User user) {
        this.title = articleRequest.getTitle();
        this.content = articleRequest.getContent();
        this.category = category;
        this.user = user;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @JsonIgnore
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (!Objects.equals(id, article.id)) return false;
        if (!Objects.equals(title, article.title)) return false;
        if (!Objects.equals(content, article.content)) return false;
        if (!Objects.equals(date, article.date)) return false;
        return Objects.equals(category, article.category);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
