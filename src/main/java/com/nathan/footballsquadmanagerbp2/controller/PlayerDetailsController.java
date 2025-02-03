package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.service.AlertService;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.service.PositionService;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class PlayerDetailsController {
    AlertService alertService = new AlertService();
    PlayerService playerService = new PlayerService();
    PositionService positionService = new PositionService();

    public boolean ValidateFields(TextField firstName, TextField lastName, TextField age, ComboBox<String> prefFoot, TextField shirtNumber, ComboBox<String> status, ComboBox<String> favPos, TextField otherPos) {
        try {
            String txtFirstName = firstName.getText();
            String txtLastName = lastName.getText();
            int intAge = Integer.parseInt(age.getText());
            String txtPrefFoot = prefFoot.getValue();
            int intShirtNumber = Integer.parseInt(shirtNumber.getText());
            String txtStatus = status.getValue();
            String txtFavPos = favPos.getValue();
            String txtOtherPos = otherPos.getText();

            List<String> positions = Arrays.stream(txtOtherPos.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());

            //TODO: txtOtherPos, parse String into ArrayList (remove ,)

            if (txtPrefFoot == null || txtStatus == null) {
                alertService.getAlert("Please fill in the preferred foot and the status of the player");
                return false;
            } else if (intShirtNumber < 0 || intShirtNumber > 99) {
                alertService.getAlert("Shirt number must be between 0 and 99");
                return false;
            } else if (txtFirstName.length() > 15) {
                alertService.getAlert("First name must be less than 16 characters");
                return false;
            } else if (txtLastName.length() > 20) {
                alertService.getAlert("Last name must be less than 21 characters");
                return false;
            } else if (intAge < 15 || intAge > 65) {
                alertService.getAlert("Age must be between 15 and 65");
                return false;
            } else if (!positionService.checkIfPositionExists(positions)) {
                alertService.getAlert("Please enter the other positions in this format: 'RWB, CDM, LB' etc.");
                return false;
            }

            playerService.insertPlayer(txtFirstName, txtLastName, intAge, txtPrefFoot, intShirtNumber, txtStatus, txtFavPos, positions);
            alertService.getAlert("Player saved successfully");
            return true;
        }

        // If formats are incorrect, show error.
        catch (NumberFormatException ex) {
            alertService.getAlert("Please enter the correct format details.");
            throw new RuntimeException(ex);
        }
    }
}
