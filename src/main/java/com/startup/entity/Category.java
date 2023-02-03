package com.startup.entity;

import javax.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<Article> articleList = new ArrayList<>();

    public Category(Article article) {
        this.name = article.getCategory();
        articleList.add(article);
    }

    public Category(String name, List<Article> articleList) {
        this.name = name;
        this.articleList = new ArrayList<>(articleList);
    }

    public Category() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public void addArticleToList(Article article) {
        articleList.add(article);
    }
}
