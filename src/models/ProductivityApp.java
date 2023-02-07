package models;

public class ProductivityApp extends App{
    //constructor
    public ProductivityApp(Developer developer, String appName, double appSize, double appVersion, double appCost) {
        super(developer, appName, appSize, appVersion, appCost);
    }

    //methods
    public String appSummaryCondensed() {
        return super.appSummary();
    }

    public boolean isRecommendedApp(){
        if (getAppCost() >= 1.99 && calculateRating() > 3.0) {
            return true;
        }
        else{
            return false;
        }
    }

    //toString
    @Override
    public String toString() {
        return super.toString();
    }
}
