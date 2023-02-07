package main;

import controllers.AppStoreAPI;
import controllers.DeveloperAPI;
import models.*;
import utils.ScannerInput;
import utils.Utilities;

public class Driver {

    //TODO Some skeleton code has been given to you.
    //     Familiarise yourself with the skeleton code...run the menu and then review the skeleton code.
    //     Then start working through the spec.

    private DeveloperAPI developerAPI = new DeveloperAPI();
    private AppStoreAPI appStoreAPI = new AppStoreAPI();

    public static void main(String[] args) {
        new Driver().start();
    }

    public void start() {
        loadAllData();
        runMainMenu();
    }

    private int mainMenu() {
        System.out.println("""
                 -------------App Store------------
                |  1) Developer - Management MENU  |
                |  2) App - Management MENU        |
                |  3) Reports MENU                 |
                |----------------------------------|
                |  4) Search                       |
                |  5) Sort                         |
                |----------------------------------|
                |  6) Recommended Apps             |
                |  7) Random App of the Day        |
                |  8) Simulate ratings             |
                |----------------------------------|
                |  20) Save all                    |
                |  21) Load all                    |
                |----------------------------------|
                |  0) Exit                         |
                 ----------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runDeveloperMenu();
                case 2 -> runAppMenu();
                case 3 -> runReportsMenu();
                case 4 -> searchAppsBySpecificCriteria();
                case 5 -> sortAppsByName();
                case 6 -> printRecommendedApps();
                case 7 -> printRandomAppOfTheDay();
                case 8 -> simulateRatings();
                case 20 -> saveAllData();
                case 21 -> loadAllData();
                default -> System.out.println("Invalid option entered: " + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int developerMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Add a developer       |
                |   2) List developer        |
                |   3) Update developer      |
                |   4) Delete developer      |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDeveloperMenu() {
        int option = developerMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addDeveloper();
                case 2 -> System.out.println(developerAPI.listDevelopers());
                case 3 -> updateDeveloper();
                case 4 -> deleteDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerMenu();
        }
    }

    private void addDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        String developerWebsite = ScannerInput.validNextLine("Please enter the developer website: ");

        if (developerAPI.addDeveloper(new Developer(developerName, developerWebsite))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void updateDeveloper() {
        System.out.println(developerAPI.listDevelopers());
        Developer developer = readValidDeveloperByName();
        if (developer != null) {
            String developerWebsite = ScannerInput.validNextLine("Please enter new website: ");
            if (developerAPI.updateDeveloperWebsite(developer.getDeveloperName(), developerWebsite))
                System.out.println("Developer Website Updated");
            else
                System.out.println("Developer Website NOT Updated");
        } else
            System.out.println("Developer name is NOT valid");
    }

    private void deleteDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.removeDeveloper(developerName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private Developer readValidDeveloperByName() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            return developerAPI.getDeveloperByName(developerName);
        } else {
            return null;
        }
    }

    //--------------------------------------------------
    //  Developer Management - Menu Items
    //--------------------------------------------------
    private int appMenu() {
        System.out.println("""
                 -------- App Menu-----------
                |   1) Add an App            |
                |   2) Update an App         |
                |   3) Delete app            |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAppMenu() {
        int option = appMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addApp();
                case 2 -> updateApp();
                case 3 -> deleteApp();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appMenu();
        }
    }

    private void addApp() {
        boolean isAdded = false;

        int option = ScannerInput.validNextInt("""
                ---------------------------------
                |   1) Add a Game App            |
                |   2) Add a Productivity Post   |
                |   3) Add an Education App      |
                ---------------------------------
                ==>> """);

        switch (option) {
            case 1 -> {
                String developerName = ScannerInput.validNextLine("Please enter valid developer Name: ");
                while (!developerAPI.isValidDeveloper(developerName)) {
                    developerName = ScannerInput.validNextLine("Please enter valid developer Name: ");
                }
//        when you input this developer name, use the thing to search for valid developer
//        this then returns a developer object which you can pass into the addApp bit
                String appName = ScannerInput.validNextLine("Please enter the app name: ");
                double appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                while (!Utilities.validRange(appSize, 1, 1000)) {
                    appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                }
                double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                while (!Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
                    appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                }
                double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                while (!Utilities.greaterThanOrEqualTo(appCost, 0.0)) {
                    appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                }
                char currentApp = ScannerInput.validNextChar("Is this app multiplayer? ");
                boolean isMultiplayer = Utilities.YNtoBoolean(currentApp);

                isAdded = appStoreAPI.addApp(new GameApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, isMultiplayer));
            }

            case 2 -> {
                String developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                while (!developerAPI.isValidDeveloper(developerName)) {
                    developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                }
                String appName = ScannerInput.validNextLine("Please enter the App name :");
                double appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                while (!Utilities.validRange(appSize, 1, 1000)) {
                    appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                }
                double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                while (!Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
                    appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                }
                double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                while (!Utilities.greaterThanOrEqualTo(appCost, 0.0)) {
                    appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                }

                isAdded = appStoreAPI.addApp(new ProductivityApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost));

            }

            case 3 -> {
                String developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                while (!developerAPI.isValidDeveloper(developerName)) {
                    developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                }
                String appName = ScannerInput.validNextLine("Please enter the App name :");
                double appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                while (!Utilities.validRange(appSize, 1, 1000)) {
                    appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                }
                double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                while (!Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
                    appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                }
                double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                while (!Utilities.greaterThanOrEqualTo(appCost, 0.0)) {
                    appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                }
                int level = ScannerInput.validNextInt("Please enter a valid level: ");
                while (!Utilities.validRange(level, 1, 10)) {
                    level = ScannerInput.validNextInt("Please enter a valid level");
                }
               isAdded = appStoreAPI.addApp(new EducationApp(developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, level));
            }
        }
        if (isAdded) {
            System.out.println("App added successfully!");
        } else {
            System.out.println("No App Added");
        }
    }

    private void updateApp() {
        if (appStoreAPI.numberOfApps() > 0) {
            boolean isUpdated = false;

            int option = ScannerInput.validNextInt("""
                    ----------------------------------
                    |   1) Update a Game App          |
                    |   2) Update a Productivity Post |
                    |   3) Update an Education App    |
                    ----------------------------------
                    ==>> """);

            switch (option) {
                case 1 -> {
                    appStoreAPI.listAllGameApps();
                    if (appStoreAPI.numberOfApps() > 0) {
                        int indexToUpdate = ScannerInput.validNextInt("Please enter the valid index of the app you would like to update: ");
                        if (appStoreAPI.isValidIndex(indexToUpdate)) {
                            String developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                            while (!developerAPI.isValidDeveloper(developerName)) {
                                developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                            }
                            String appName = ScannerInput.validNextLine("Please enter the app name: ");
                            double appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                            while (!Utilities.validRange(appSize, 1, 1000)) {
                                appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                            }
                            double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                            while (!Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
                                appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                            }
                            double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                            while (!Utilities.greaterThanOrEqualTo(appCost, 0.0)) {
                                appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                            }
                            char currentApp = ScannerInput.validNextChar("Is this app multiplayer? ");
                            boolean isMultiplayer = Utilities.YNtoBoolean(currentApp);


                            isUpdated = appStoreAPI.updateGameApp(indexToUpdate, developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, isMultiplayer);
                        }
                    }
                }
                case 2 -> {
                    appStoreAPI.listAllProductivityApps();
                    if (appStoreAPI.numberOfApps() > 0) {
                        int indexToUpdate = ScannerInput.validNextInt("Please enter the valid index of the app you would like to update: ");
                        if (appStoreAPI.isValidIndex(indexToUpdate)) {
                            String developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                            while (!developerAPI.isValidDeveloper(developerName)) {
                                developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                            }
                            String appName = ScannerInput.validNextLine("Please enter the app name: ");
                            double appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                            while (!Utilities.validRange(appSize, 1, 1000)) {
                                appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                            }
                            double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                            while (!Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
                                appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                            }
                            double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                            while (!Utilities.greaterThanOrEqualTo(appCost, 0.0)) {
                                appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                            }
                            isUpdated = appStoreAPI.updateProductivityApp(indexToUpdate, developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost);

                        }
                    }
                }
                case 3 -> {
                    appStoreAPI.listAllEducationApps();
                    if (appStoreAPI.numberOfApps() > 0) {
                        int indexToUpdate = ScannerInput.validNextInt("Please enter the valid index of the app you would like to update: ");
                        if (appStoreAPI.isValidIndex(indexToUpdate)) {
                            String developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                            while (!developerAPI.isValidDeveloper(developerName)) {
                                developerName = ScannerInput.validNextLine("Please enter valid developer name: ");
                            }
                            String appName = ScannerInput.validNextLine("Please enter the app name: ");
                            double appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                            while (!Utilities.validRange(appSize, 1, 1000)) {
                                appSize = ScannerInput.validNextDouble("Please enter the app size: ");
                            }
                            double appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                            while (!Utilities.greaterThanOrEqualTo(appVersion, 1.0)) {
                                appVersion = ScannerInput.validNextDouble("Please enter the app version: ");
                            }
                            double appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                            while (!Utilities.greaterThanOrEqualTo(appCost, 0.0)) {
                                appCost = ScannerInput.validNextDouble("Please enter the app cost: ");
                            }
                            int level = ScannerInput.validNextInt("Please enter the app level: ");
                            while (!Utilities.validRange(level, 1, 10)) {
                                level = ScannerInput.validNextInt("Please enter the app level: ");
                            }
                            isUpdated = appStoreAPI.updateEducationApp(indexToUpdate, developerAPI.getDeveloperByName(developerName), appName, appSize, appVersion, appCost, level);
                        }
                    }
                }
                default -> System.out.println("Invalid option entered: " + option);
            }
            if (isUpdated) {
                System.out.println("App updated successfully!");
            } else {
                System.out.println("App NOT updated");
            }
        }
        else {
            System.out.println("No apps available to update");
        }
    }

    private void deleteApp() {
        appStoreAPI.listAllApps();
        if (appStoreAPI.numberOfApps() > 0) {
            int indexToDelete = ScannerInput.validNextInt("Please enter the index number of the app you want to delete: ");
            App appToDelete = appStoreAPI.deleteAppByIndex(indexToDelete);
            if (appToDelete != null) {
                System.out.println("App deleted successfully! Deleted app: " + appToDelete.getAppName());
            } else {
                System.out.println("Delete NOT successful");
            }
        }
    }

    //--------------------------------------------------
    //  Reports Management - Menu Items
    //--------------------------------------------------
    private int reportsMenu() {
        System.out.println("""
                 -------Developer Menu-------
                |   1) Apps Overview         |
                |   2) Developers Overview   |
                |   0) RETURN to main menu   |
                 ----------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runReportsMenu() {
        int option = reportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runAppsOverview();
                case 2 -> runDevelopersOverview();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = reportsMenu();
        }
    }

    //--------------------------------------------------
    //  App Overview - Menu Items
    //--------------------------------------------------
    private int appOverviewMenu() {
        System.out.println("""
                 ----------App Reports Menu----------
                |   1) List all Apps                 |
                |   2) Number of Apps                |
                |   3) Summary of all Apps           |
                |   4) List of all game apps         |
                |   5) List of all productivity apps |
                |   6) List of all education apps    |
                |   7) List of apps by name          |
                |   8) List of all apps by rating    |
                |   9) List of recommended apps      |
                |   0) RETURN to main menu           |
                 ------------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runAppsOverview() {
        int option = appOverviewMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> listOfApps();
                case 2 -> numberOfApps();
                case 3 -> summaryOfApps();
                case 4 -> listOfGameApps();
                case 5 -> listOfProductivityApps();
                case 6 -> listOfEducationApps();
                case 7 -> listOfAppsByName();
                case 8 -> listOfAppsByRating();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = appOverviewMenu();
        }
    }

    private void listOfApps() {
        System.out.println("List of all apps: ");
        System.out.println(appStoreAPI.listAllApps());
    }

    private void numberOfApps() {
        System.out.println(appStoreAPI.numberOfApps() + "apps exist");
    }

    private void summaryOfApps() {
        System.out.println("Here is the summary of all apps: ");
        System.out.println(appStoreAPI.listSummaryOfAllApps());
    }

    private void listOfGameApps() {
        System.out.println("List of all game apps: " + appStoreAPI.listAllGameApps());
    }

    private void listOfProductivityApps() {
        System.out.println("List of all productivity apps: " + appStoreAPI.listAllProductivityApps());
    }

    private void listOfEducationApps() {
        System.out.println("List of all education apps: " + appStoreAPI.listAllEducationApps());
    }

    private void listOfAppsByName() {
        String appName = ScannerInput.validNextLine("Please enter the name of the apps: ");
        System.out.println(appStoreAPI.listAllAppsByName(appName));
    }

    private void listOfAppsByRating() {
        double rating = ScannerInput.validNextDouble("Please enter the rating you want the apps to be or to be over: ");
        System.out.println(appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(rating));
    }

    private void printRecommendedApps() {
        System.out.println("List of Recommended Apps: " + appStoreAPI.listAllRecommendedApps());
    }

    //--------------------------------------------------
    //  Developer Overview - Menu Items
    //--------------------------------------------------
    private int developerOverviewMenu() {
        System.out.println("""
                 -------------App Reports Menu------------
                |   1) List apps by chosen developer      |
                |   2) Number of Apps by chosen developer |
                 -----------------------------------------""");
        return ScannerInput.validNextInt("==>> ");
    }

    private void runDevelopersOverview() {
        int option = developerOverviewMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> listOfAppsByDeveloper();
                case 2 -> numberOfAppsByDeveloper();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.validNextLine("\n Press the enter key to continue");
            option = developerOverviewMenu();
        }
    }

    private void listOfAppsByDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developer's name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            System.out.println("Apps by chosen developer: " + appStoreAPI.listAllAppsByChosenDeveloper(developerAPI.getDeveloperByName(developerName)));
        }
    }

    private void numberOfAppsByDeveloper() {
        String developerName = ScannerInput.validNextLine("Please enter the developers name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            System.out.println(appStoreAPI.numberOfAppsByChosenDeveloper(developerAPI.getDeveloperByName(developerName)));
        }
    }


    // sorting and searching methods

    private void sortAppsByName() {
        appStoreAPI.sortAppsByNameAscending();
        System.out.println("Apps by names ascending: " + appStoreAPI.listAllApps());
    }


    //--------------------------------------------------
    // TODO UNCOMMENT THIS CODE as you start working through this class
    //--------------------------------------------------
    private void searchAppsBySpecificCriteria() {
        System.out.println("""
                What criteria would you like to search apps by:
                  1) App Name
                  2) Developer Name
                  3) Rating (all apps of that rating or above)""");
        int option = ScannerInput.validNextInt("==>> ");
        switch (option) {
            // TODO Search methods below
             case 1 -> searchAppsByName();
             case 2 -> searchAppsByDeveloper(readValidDeveloperByName());
             case 3 -> searchAppsEqualOrAboveAStarRating();
             default -> System.out.println("Invalid option");
        }
    }

    private void searchAppsByName(){
        String appName = ScannerInput.validNextLine("Please enter the app name: ");
        System.out.println(appStoreAPI.listAllAppsByName(appName));
    }

    private void searchAppsByDeveloper (Developer readValidDeveloperByName) {
        String developerName = ScannerInput.validNextLine("Please enter the developer name: ");
        if (developerAPI.isValidDeveloper(developerName)) {
            System.out.println(appStoreAPI.listAllAppsByChosenDeveloper(developerAPI.getDeveloperByName(developerName)));
        }
    }

    private void searchAppsEqualOrAboveAStarRating(){
        double rating = ScannerInput.validNextDouble("Please enter the rating you wish to search apps for: ");
        System.out.println(appStoreAPI.listAllAppsAboveOrEqualAGivenStarRating(rating));

    }


    //--------------------------------------------------
    // TODO UNCOMMENT THIS COMPLETED CODE as you start working through this class
    //--------------------------------------------------
    private void simulateRatings() {
        // simulate random ratings for all apps (to give data for recommended apps and reports etc.).
         if (appStoreAPI.numberOfApps() > 0) {
            System.out.println("Simulating ratings...");
            appStoreAPI.simulateRatings();
            System.out.println(appStoreAPI.listSummaryOfAllApps());
        } else {
            System.out.println("No apps");
        }
    }

    private void printRandomAppOfTheDay(){
        System.out.println("The random App of the day is: " + appStoreAPI.randomApp());
    }

    //--------------------------------------------------
    //  Persistence Menu Items
    //--------------------------------------------------

    private void saveAllData() {

        try {
            appStoreAPI.save();
            developerAPI.save();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }

        // TODO try-catch to save the developers to XML file
        // TODO try-catch to save the apps in the store to XML file
    }

    private void loadAllData() {
        try {
            developerAPI.load();
            appStoreAPI.load();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }
        // TODO try-catch to load the developers from XML file
        // TODO try-catch to load the apps in the store from XML file
    }

}

