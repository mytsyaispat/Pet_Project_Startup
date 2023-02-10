package com.startup.logic.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "category")
//@Table(name = "category",
//        uniqueConstraints = @UniqueConstraint(name = "UniqueNameAndParentId", columnNames = {"name", "parent_id"})
//)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "parent_id", referencedColumnName = "id")
//    private Category parent = null;

    public Category(String name) {
        this.name = name;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {}

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

    @JsonProperty(value = "category")
    public void setName(String name) {
        this.name = name;
    }

//    public Category getParent() {
//        return parent;
//    }
//
//    @JsonIgnore
//    public void setParent(Category parent) {
//        this.parent = parent;
//    }
}
