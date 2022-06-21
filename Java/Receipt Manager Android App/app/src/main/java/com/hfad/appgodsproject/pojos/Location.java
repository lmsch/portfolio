package com.hfad.appgodsproject.pojos;

public class Location {

    private long id;
    private String description;

    public Location(String description) {
        this.description = description;
    }

    public Location() {}

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
