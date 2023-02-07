package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.*;

import static java.lang.Math.random;
import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI {

    //field

    private List<App> apps = new ArrayList<App>();
    private Random randomGenerator;

    //getter
    public List<App> getApps() {
        return apps;
    }

    //setter
    public void setApps(List<App> apps) {
        this.apps = apps;
    }

    //CRUD methods

    // adds an app object as a parameter through the arraylist
    public boolean addApp(App app){
        return apps.add(app);
    }

    // This method deletes an App at the index parameter (if that index exists) and returns the deleted app object.
    // If the index does not exist in the app list, then null should be returned
    public App deleteAppByIndex(int indexToDelete) {
        if (isValidIndex(indexToDelete)) {
            return apps.remove(indexToDelete);
        }
        return null;
    }

    //find App at specific index location, returns object or null if no object at index location
    public App getAppByIndex(int index){
        if (isValidIndex(index)) {
            return apps.get(index);
        }
        return null;
    }

    //runs a loop through notes and returns the title that contains the search string

    public String getAppByName(String nameOfApp) {
        String results = "";
        if (apps.isEmpty()) return "No apps available";
        for (int i = 0; i < apps.size(); i++) {
            if (apps.get(i).getAppName().contains(nameOfApp)) {
                results += i + apps.get(i).getAppName();
            }
        }
        if (results.length() == 0) return "No Apps found called: " + nameOfApp;
        return results;
    }

    //updating methods
    public boolean updateGameApp(int indexToUpdate, Developer developer ,String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer){
        App foundGame = getAppByIndex(indexToUpdate);

        if ((foundGame != null) && (foundGame instanceof GameApp)){
            foundGame.setDeveloper(developer);
            ((GameApp) foundGame).setAppName(appName);
            ((GameApp) foundGame).setAppSize(appSize);
            ((GameApp) foundGame).setAppVersion(appVersion);
            ((GameApp) foundGame).setAppCost(appCost);
            ((GameApp) foundGame).setMultiplayer(isMultiplayer);
            return true;

        }

        return false;
    }

    public boolean updateProductivityApp(int indexToUpdate, Developer developer ,String appName, double appSize, double appVersion, double appCost){
        App foundProductivity = getAppByIndex(indexToUpdate);

        if ((foundProductivity != null) && (foundProductivity instanceof ProductivityApp)){
            foundProductivity.setDeveloper(developer);
            ((ProductivityApp) foundProductivity).setAppName(appName);
            ((ProductivityApp) foundProductivity).setAppSize(appSize);
            ((ProductivityApp) foundProductivity).setAppVersion(appVersion);
            ((ProductivityApp) foundProductivity).setAppCost(appCost);
            return true;

        }

        return false;
    }

    public boolean updateEducationApp(int indexToUpdate, Developer developer ,String appName, double appSize, double appVersion, double appCost, int level){
        App foundEducation = getAppByIndex(indexToUpdate);

        if ((foundEducation != null) && (foundEducation instanceof EducationApp)){
            foundEducation.setDeveloper(developer);
            ((EducationApp) foundEducation).setAppName(appName);
            ((EducationApp) foundEducation).setAppSize(appSize);
            ((EducationApp) foundEducation).setAppVersion(appVersion);
            ((EducationApp) foundEducation).setAppCost(appCost);
            ((EducationApp) foundEducation).setLevel(level);
            return true;

        }

        return false;
    }

    // Reporting Methods


    // if no apps exist return no apps, if they do exist, loop through apps and find the index of each app and get each app
    // and add the app to the string, return string
    public String listAllApps(){
        String listOfAllApps = "";
        if (apps.isEmpty()) {
            return "No Apps";
        }
        else{
            for (int i = 0; i < apps.size(); i++){
                listOfAllApps += i + apps.indexOf(i) + ": " + apps.get(i).appSummary() + "\n";
            }
            return listOfAllApps;
        }
    }

    public int numberOfApps(){
        int total = 0;
        for (int i = 0; i < apps.size(); i++){
            total++;
        }
        return total;
    }

    // if no apps exist return no apps, if they do exist, loop through apps and find the index of each app and get each app
    // summary and add the app along with its summary to the string, return string
    public String listSummaryOfAllApps() {
        String listOfAllSummaries = "";
        if (apps.isEmpty()){
            return "No Apps";
        }
        else{
            for (App app: apps){
                listOfAllSummaries += apps.indexOf(app) + app.appSummary() + "\n";
            }
            return listOfAllSummaries;
        }
    }

    // if no apps exist, return no apps
    // if apps do exist, loop through and in instance of GameApp, add the app index and summary to the string
    // return the string or return no game apps
    public String listAllGameApps() {
        String listOfAllGameApps = "";
        if(apps.isEmpty()){
            return "No Apps";
        }
        else{
            for (App app: apps){
                if (apps instanceof GameApp){
                  listOfAllGameApps += apps.indexOf(app) + ": " + app.appSummary();
                }
            }
            if (listOfAllGameApps.length() == 0) return "No Game apps";
            return listOfAllGameApps;
        }
    }

    // if no apps exist, return no apps
    // if apps do exist, loop through and in instance of EducationApp, add the app index and summary to the string
    // return the string or no education apps
    public String listAllEducationApps() {
        String listOfAllEducationApps = "";
        if(apps.isEmpty()){
            return "No Apps";
        }
        else{
            for (App app: apps){
                if (apps instanceof EducationApp){
                    listOfAllEducationApps += apps.indexOf(app) + ": " + app.appSummary();
                }
            }
            if (listOfAllEducationApps.length() == 0) return "No Education apps";
            return listOfAllEducationApps;
        }
    }

    // if no apps exist, return no apps
    // if apps do exist, loop through and in instance of ProductivityApp, add the app index and summary to the string
    // return the string or no productivity apps
    public String listAllProductivityApps() {
        String listOfAllProductivityApps = "";
        if(apps.isEmpty()){
            return "No Apps";
        }
        else{
            for (App app: apps){
                if (apps instanceof ProductivityApp){
                    listOfAllProductivityApps += apps.indexOf(app) + ": " + app.appSummary();
                }
            }
            if (listOfAllProductivityApps.length() == 0) return "No Productivity apps";
            return listOfAllProductivityApps;
        }
    }

    // if no apps exist, return no apps
    // else, loop through apps and get each app name that contains the search string entered
    // if no apps contain search string, return no apps under said name
    // else return results
    public String listAllAppsByName(String searchString){
        String results = "";
        if(apps.isEmpty()) return "No Apps";
        for(int i = 0; i < apps.size(); i++){
            if (apps.get(i).getAppName().equalsIgnoreCase(searchString)){
                results += i + apps.get(i).getAppName();
            }
        }
        if (results.length() == 0) return "No Apps for name " + searchString + " exist";

        return results;
    }

    // if no apps exist return no apps
    // else, loop through the apps list and get each app which rating is equal to or greater than the entered rating you searched for
    // if no apps are above or equal to the entered rating, return no apps
    // else return results
    public String listAllAppsAboveOrEqualAGivenStarRating(double rating){
        String results = "";
        if (apps.isEmpty()) return "No Apps";
        for (int i = 0; i < apps.size(); i++){
            if (apps.get(i).calculateRating() >= rating){
                results += i + apps.get(i).calculateRating();
            }
        }
        if (results.length() == 0) return "No Apps have a rating of " + rating + " or above";

        return results;
    }

    // if no apps exist return no apps
    // else, loop through the apps list and get each app which is recommeneded according to each sub-class
    // if no apps are recommended, return no apps
    // else return results
    public String listAllRecommendedApps(){
        String results = "";
        if (apps.isEmpty()) return "No Apps";
        for (int i = 0; i < apps.size(); i++){
            if(apps.get(i).isRecommendedApp()){
                results += i + apps.get(i).getAppName();
            }
        } if (results.length() == 0) return "No recommended apps";

        return results;
    }


    //run a loop through apps
    //if apps are under the developer name chosen return the apps
    //if no apps are under the chosen developer, return no apps foe developer
    public String listAllAppsByChosenDeveloper(Developer developer){
        String results = "";
        if(apps.isEmpty()) return "No Apps";
        for (int i = 0; i < apps.size(); i++){
            if (apps.get(i).getDeveloper().getDeveloperName() == developer.getDeveloperName()){
                results += i + apps.get(i).getDeveloper().getDeveloperName();
            }
        } if (results.length() == 0) return "No apps for developer: " + developer;
        return results;
    }

    //run loop through app list
    //find each app made by the developer
    //add 1 to each app by the developer and return total;
    public int numberOfAppsByChosenDeveloper(Developer developer){
        int total = 0;
        for (int i = 0; i < apps.size(); i++){
            if(apps.get(i).getDeveloper().equals(developer)){
                total++;
            }
        } return total;
    }

    public App randomApp(){
        if (apps.size() < 0) return null;
        int index = randomGenerator.nextInt(apps.size());
        return apps.get(index);
    }

    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)

    public void simulateRatings(){
        for (App app :apps) {
            app.addRating(generateRandomRating());
        }
    }

    //---------------------
    // Validation methods
    //---------------------
    // TODO UNCOMMENT THIS COMPlETED method as you start working through this class
    //---------------------

    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }

    public boolean isValidAppName(String name) {
        for (int i = 0; i < apps.size(); i++){
            if(apps.get(i).getAppName() == name){
                return true;
            }
        } return false;
    }
    // sorting methods

    // Selection sort algorithm for physically sorting the list of apps by app name ascending.

    public void sortAppsByNameAscending()
    {
        for (int i = apps.size() -1; i >= 0; i--)
        {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++)
            {
                if (apps.get(j).getAppName().compareTo(apps.get(highestIndex).getAppName()) > 0) {
                    highestIndex = j;
                }
            }
            swapApps(apps, i, highestIndex);
        }
    }

    private void swapApps(List<App> apps, int i, int j) {
        App smaller = apps.get(i);
        App bigger = apps.get(j);

        apps.set(i,bigger);
        apps.set(j,smaller);
    }

    //---------------------
    // Persistence methods
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED CODE block as you start working through this class
    //---------------------


    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        // list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (List<App>) in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName(){
        return "apps.xml";
    }

}
