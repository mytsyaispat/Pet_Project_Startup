package com.startup.logic.controller.entity;

public final class CategoryParent {

    private final String category;
    private final String parent;

    public CategoryParent(String category, String parent) {
        this.category = category;
        this.parent = parent;
    }

    public String getCategory() {
        return category;
    }

    public String getParent() {
        return parent;
    }
}
