package com.hfad.appgodsproject.pojos;

public class Category {

    private long id;
    private String description;

    public Category(String description) {
        this.description = description;
    }

    public Category() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
