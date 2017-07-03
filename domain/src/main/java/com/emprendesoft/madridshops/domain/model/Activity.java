package com.emprendesoft.madridshops.domain.model;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    // TODO: ver si hace falta
//    public Activity setLogitude(float logitude) {
//        this.longitude = logitude;
//        return this;
//    }
}






























