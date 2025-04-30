package com.example.myapplication;

public class ActivitiesFeatures {
    private String ActivityName;
    private String Location;
    private String LocationLink;
    private String PricesInArea;
    private String TargetAudience;
    private String about;
    private String imageLink;
    private String type;

    // Default constructor required for Firestore
    public ActivitiesFeatures() {
    }

    public ActivitiesFeatures(String activityName, String location, String locationLink, String pricesInArea, String targetAudience, String about, String imageLink, String type) {
        ActivityName = activityName;
        this.Location = location;
        this.LocationLink = locationLink;
        this.PricesInArea = pricesInArea;
        this.TargetAudience = targetAudience;
        this.about = about;
        this.imageLink = imageLink;
        this.type = type;
    }

    // Getters and setters
    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        this.ActivityName = activityName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getPricesInArea() {
        return PricesInArea;
    }

    public void setPricesInArea(String pricesInArea) {
        this.PricesInArea = pricesInArea;
    }

    public String getTargetAudience() {
        return TargetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.TargetAudience = targetAudience;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocationLink() {
        return LocationLink;
    }

    public void setLocationLink(String locationLink) {
        this.LocationLink = locationLink;
    }
    public String getImageLink() {
        return imageLink;
    }
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
