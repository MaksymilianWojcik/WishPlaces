package com.example.mwojcik.wishplaces.dto;

public class WishPlace {

    int id;
    String name;
    String summary;
    String description;
    String latitude;
    String longitude;

    public WishPlace(){}

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

    public WishPlace(int id, String name, String summary, String description, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public WishPlace(String name, String summary, String description, String latitude, String longitude) {
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
