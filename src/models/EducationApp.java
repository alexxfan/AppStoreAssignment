package models;

import utils.Utilities;

public class EducationApp extends App {
    //fields

    private int level = 0;


    //constructor
    public EducationApp(Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        super(developer, appName, appSize, appVersion, appCost);
        setLevel(level);
    }

    //getter
    public int getLevel() {
        return level;
    }

    //setter
    public void setLevel(int level) {
        if (Utilities.validRange(level,1,10)) {
            this.level = level;
        }
    }

    public boolean isRecommendedApp() {
        if (getAppCost() > .99 && calculateRating() >= 3.5 && getLevel() >= 3){
            return true;
        }
        else {
            return false;
        }
    }


    public String appSummary() {
        return super.appSummary() + "level " + level;
    }

    @Override
    public String toString() {
        return super.toString() + "Level: " + level;
    }
}
