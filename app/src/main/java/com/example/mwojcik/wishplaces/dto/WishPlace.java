package com.example.mwojcik.wishplaces.dto;

public class WishPlace {

    int id;
    String name;
    String summary;
    String description;


    public WishPlace(String name, String summary, String description) {
        this.name = name;
        this.summary = summary;
        this.description = description;
    }

    public WishPlace(int id, String name, String summary, String description) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
