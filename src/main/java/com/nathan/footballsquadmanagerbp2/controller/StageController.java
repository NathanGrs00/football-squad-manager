package com.nathan.footballsquadmanagerbp2.controller;

import javafx.stage.Stage;

public class StageController {
    // Class for setting and getting the primaryStage.
    private static Stage primaryStage;

    private StageController() {
    }

    public static void setPrimaryStage(Stage stage) {

        if (primaryStage == null) {
            primaryStage = stage;
        }
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
