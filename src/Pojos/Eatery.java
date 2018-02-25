package Pojos;

import java.io.Serializable;

public class Eatery implements Serializable{

    private String eateryName;
    private int ratingAvg;
    private String foodDescription;
    private int latitude, longitude;

    public Eatery(String eateryName, int ratingAvg, String foodDescription, int latitude, int longitude) {
        this.eateryName = eateryName;
        this.ratingAvg = ratingAvg;
        this.foodDescription = foodDescription;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*public Eatery(String eateryName, int ratingAvg, int locationId, String foodDescription) {
        this.eateryName = eateryName;
        this.ratingAvg = ratingAvg;
        this.locationId = locationId;
        this.foodDescription = foodDescription;
    }*/

    public String getEateryName() {
        return eateryName;
    }

    public int getRatingAvg() {
        return ratingAvg;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }
}
