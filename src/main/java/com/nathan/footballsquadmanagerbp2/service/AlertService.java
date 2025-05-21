package com.nathan.footballsquadmanagerbp2.service;

import javafx.scene.control.Alert;

// This class is used to create an alert.
public class AlertService {
    // This method is used to create an alert with a message.
    public void getAlert(String message){
        // Create an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        // Set the title, header and content of the alert
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Show the alert
        alert.showAndWait();
    }
}
