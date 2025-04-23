package com.example.myapplication;

public class ActivitiesFeatures {
    private String ActivityName;
    private String Location;
    private String PricesInArea;
    private String TargetAudience;
    private String about;
    private String type;

    public ActivitiesFeatures(String activityName, String location, String pricesInArea, String targetAudience, String about, String type) {
        this.ActivityName = activityName;
        this.Location = location;
        this.PricesInArea = pricesInArea;
        this.TargetAudience = targetAudience;
        this.about = about;
        this.type = type;
    }

    public ActivitiesFeatures() {}

    public String getActivityName() {
        return ActivityName;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPricesInArea() {
        return PricesInArea;
    }

    public void setPricesInArea(String pricesInArea) {
        PricesInArea = pricesInArea;
    }

    public String getTargetAudience() {
        return TargetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        TargetAudience = targetAudience;
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
}
