package com.nathan.footballsquadmanagerbp2.controller;

import javafx.stage.Stage;

public class StageController {
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
