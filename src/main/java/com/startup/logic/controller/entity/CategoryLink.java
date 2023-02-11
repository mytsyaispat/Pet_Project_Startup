package com.startup.logic.controller.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CategoryLink {
    @NotBlank(message = "parent_name must not be empty")
    @JsonProperty(value = "parent_name")
    private String parentName;
    @NotBlank(message = "child_name must not be empty")
    @JsonProperty(value = "child_name")
    private String childName;

    public CategoryLink(String parentName, String childName) {
        this.parentName = parentName;
        this.childName = childName;
    }

    public CategoryLink() {}

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
