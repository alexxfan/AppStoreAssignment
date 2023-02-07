package models;

import utils.Utilities;
import models.Rating;
import models.Developer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class App {
    //fields

    private Developer developer;

    private String appName = "No app name";

    private double appSize = 0;

    private double appVersion = 1.0;

    private double appCost = 0.0;

    private List<Rating> ratings = new ArrayList<>();


    //constructor

    public App(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        this.developer = developer;
        this.appName = appName;
        setAppSize(appSize);
        setAppVersion(appVersion);
        setAppCost(appCost);
    }

    // getters

    public Developer getDeveloper() {
        return developer;
    }

    public String getAppName() {
        return appName;
    }

    public double getAppSize() {
        return appSize;
    }

    public double getAppVersion() {
        return appVersion;
    }

    public double getAppCost() {
        return appCost;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    // setters

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setAppSize(double appSize) {
        if (Utilities.validRange(appSize, 1, 1000)) {
            this.appSize = appSize;
        }
    }

    public void setAppVersion(double appVersion) {
        if (Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
            this.appVersion = appVersion;
        }
    }

    public void setAppCost(double appCost) {
        if (Utilities.greaterThanOrEqualTo(appCost, 0.0)) {
            this.appCost = appCost;
        }
    }

    //methods

    public boolean addRating(Rating rating) {
        return ratings.add(rating);

    }

    public String listRatings() {
        if (ratings.isEmpty()){
            return "No ratings added yet";
        }
        else {
            String listOfRatings = "";
            for(int i = 0; i < ratings.size(); i++){
                listOfRatings += i + ": " + ratings.get(i) + "\n";
            }
            return listOfRatings;
        }
    }

    public double calculateRating() {
        double total = 0.0;
        if (ratings.isEmpty()){
            return 0.0;
        }
        else {
            for(int i = 0; i < ratings.size(); i++){
                if(ratings.get(i).getNumberOfStars() != 0){
                    total += ratings.get(i).getNumberOfStars();
                }
            }
            return total / ratings.size();
        }
    }

    public abstract boolean isRecommendedApp();


    public String appSummary() {
        return appName
                + "(V" + appVersion + ")"
                + "by " + developer
                + "€" + appCost
                + "Rating: " + calculateRating() + "\n";

    }

    //toString

    @Override
    public String toString() {
        return appName +
                "(Version " + appVersion + ")"
                + "by " + developer +
                appSize + "MB"
                + "Cost: " + appCost
                + "Ratings (" + calculateRating() + ")"
                + listRatings() + "\n";


    }
}
