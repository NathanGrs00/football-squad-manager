package com.nathan.footballsquadmanagerbp2.controller;

import javafx.stage.Stage;

// Controller to set and get the primaryStage.
public class StageController {
    // Singleton instance
    private static Stage primaryStage;

    // Empty constructor to prevent instantiation
    private StageController() {
    }

    // Method to set the primaryStage, only if it is null
    public static void setPrimaryStage(Stage stage) {
        // Check if primaryStage is null before setting it
        if (primaryStage == null) {
            // set the stage as the primaryStage, this is now the singleton instance
            primaryStage = stage;
        }
    }
    // Method to get the primaryStage
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
