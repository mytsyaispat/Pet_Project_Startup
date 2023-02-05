package com.startup.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToMany
    private List<User> userList;

    public Role() {}

    public Role(String name) {
        this.name = name.toUpperCase();
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

    public List<User> getUserList() {
        return userList;
    }

    @JsonIgnore
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
