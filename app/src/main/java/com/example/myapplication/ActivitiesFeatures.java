package com.example.myapplication;

/**
 * This class represents the features of an activity. It contains information about the activity such as its name, location, prices, target audience, description, image link, and type.
 */
public class ActivitiesFeatures {
    private String ActivityName;
    private String Location;
    private String LocationLink;
    private String PricesInArea;
    private String TargetAudience;
    private String about;
    private String imageLink;
    private String type;

    /**
     * Default constructor required for Firestore serialization.
     */
    public ActivitiesFeatures() {
    }

    /**
     * Constructor to initialize the activity with all necessary fields.
     *
     * @param activityName   The name of the activity.
     * @param location       The location of the activity.
     * @param locationLink   A link to the location of the activity.
     * @param pricesInArea   The prices in the area for the activity.
     * @param targetAudience The target audience for the activity.
     * @param about          A description of the activity.
     * @param imageLink      A link to an image representing the activity.
     * @param type           The type/category of the activity.
     */
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

    /**
     * Gets the name of the activity.
     *
     * @return The activity name.
     */
    public String getActivityName() {
        return ActivityName;
    }

    /**
     * Sets the name of the activity.
     *
     * @param activityName The name of the activity.
     */
    public void setActivityName(String activityName) {
        this.ActivityName = activityName;
    }

    /**
     * Gets the location of the activity.
     *
     * @return The location of the activity.
     */
    public String getLocation() {
        return Location;
    }

    /**
     * Sets the location of the activity.
     *
     * @param location The location of the activity.
     */
    public void setLocation(String location) {
        this.Location = location;
    }

    /**
     * Gets the prices in the area for the activity.
     *
     * @return The prices in the area.
     */
    public String getPricesInArea() {
        return PricesInArea;
    }

    /**
     * Sets the prices in the area for the activity.
     *
     * @param pricesInArea The prices in the area.
     */
    public void setPricesInArea(String pricesInArea) {
        this.PricesInArea = pricesInArea;
    }

    /**
     * Gets the target audience for the activity.
     *
     * @return The target audience for the activity.
     */
    public String getTargetAudience() {
        return TargetAudience;
    }

    /**
     * Sets the target audience for the activity.
     *
     * @param targetAudience The target audience for the activity.
     */
    public void setTargetAudience(String targetAudience) {
        this.TargetAudience = targetAudience;
    }

    /**
     * Gets the description of the activity.
     *
     * @return The description of the activity.
     */
    public String getAbout() {
        return about;
    }

    /**
     * Sets the description of the activity.
     *
     * @param about The description of the activity.
     */
    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * Gets the type/category of the activity.
     *
     * @return The type of the activity.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type/category of the activity.
     *
     * @param type The type of the activity.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the link to the location of the activity.
     *
     * @return The location link.
     */
    public String getLocationLink() {
        return LocationLink;
    }

    /**
     * Sets the link to the location of the activity.
     *
     * @param locationLink The location link.
     */
    public void setLocationLink(String locationLink) {
        this.LocationLink = locationLink;
    }

    /**
     * Gets the link to the image representing the activity.
     *
     * @return The image link.
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * Sets the link to the image representing the activity.
     *
     * @param imageLink The image link.
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
