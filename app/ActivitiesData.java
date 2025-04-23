public class ActivitiesData {
    private String id;
    private String ActivityName;
    private String Location;
    private String PricesInArea;
    private String TargetAudience;
    private String about;

    // בנאי ריק נדרש ל-Firebase
    public ActivitiesData() {
    }

    // בנאי עם פרמטרים
    public ActivitiesData(String id, String activityName, String location, String pricesInArea, String targetAudience, String about) {
        this.id = id;
        this.ActivityName = activityName;
        this.Location = location;
        this.PricesInArea = pricesInArea;
        this.TargetAudience = targetAudience;
        this.about = about;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public String getLocation() {
        return Location;
    }

    public String getPricesInArea() {
        return PricesInArea;
    }

    public String getTargetAudience() {
        return TargetAudience;
    }

    public String getAbout() {
        return about;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setActivityName(String activityName) {
        this.ActivityName = activityName;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public void setPricesInArea(String pricesInArea) {
        this.PricesInArea = pricesInArea;
    }

    public void setTargetAudience(String targetAudience) {
        this.TargetAudience = targetAudience;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
