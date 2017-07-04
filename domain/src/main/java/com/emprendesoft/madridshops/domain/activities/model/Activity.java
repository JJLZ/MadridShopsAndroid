package com.emprendesoft.madridshops.domain.activities.model;

import java.io.Serializable;

public class Activity implements Serializable {

    private long id;
    private String name;
    private String imageUrl;
    private String logoUrl;
    private String address;
    private String url;
    private String description;
    private float latitude;
    private float longitude;

    public static Activity of(long id, String name) {

        Activity activity = new Activity();

        activity.id = id;
        activity.name = name;

        return activity;
    }

    public Activity() {
    }

    public Activity setId(long id) {
        this.id = id;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Activity setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Activity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public Activity setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Activity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Activity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Activity setDescription(String description) {
        this.description = description;
        return this;
    }

    public float getLatitude() {
        return latitude;
    }

    public Activity setLatitude(float latitude) {
        this.latitude = latitude;
        return this;
    }

    public float getLongitude() {
        return longitude;
    }

    public Activity setLongitude(float longitude) {
        this.longitude = longitude;
        return this;
    }

// TODO: ver si hace falta
//    public Activity setLogitude(float logitude) {
//        this.longitude = logitude;
//        return this;
//    }
}





























