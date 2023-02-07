package models;

public class GameApp extends App {

    //fields
    private boolean isMultiplayer = false;

    //constructor
    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        this.isMultiplayer = isMultiplayer;
    }

    //getter

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    //setter
    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    public String appSummary() {
        return super.appSummary() + "Multiplayer: " + isMultiplayer;
    }

    //methods
    public boolean isRecommendedApp(){
        return (isMultiplayer = true) && (calculateRating() > 4.0);
    }

    @Override
    public String toString() {
        return super.toString() + "Multiplayer: " + isMultiplayer;
    }
}
