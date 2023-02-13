package com.startup.logic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "category",
        uniqueConstraints = @UniqueConstraint(name = "UniqueParentAndChild", columnNames = {"name", "parent_id"})
)
@JsonPropertyOrder({
        "id",
        "name",
        "parent_id"
})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "Name field must not be empty!")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "parent_id")
    private List<Category> categoryList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "parent_id")
    private Category category = null;


    public Category(String name) {
        this.name = name;
    }

    public Category(String name, List<Category> categoryList) {
        this.name = name;
        this.categoryList = new ArrayList<>(categoryList);
    }

    public Category(Long id, String name, List<Category> categoryList, Category category) {
        this.id = id;
        this.name = name;
        this.categoryList = categoryList;
        this.category = category;
    }

    public Category() {
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @JsonIgnore
    public void addCategoryToCategoryList(Category category) {
        categoryList.add(category);
    }

    @JsonIgnore
    public Category getCategory() {
        return category;
    }

    @JsonIgnore
    public void setCategory(Category category) {
        this.category = category;
    }
}
