package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.PositionDAO;
import com.nathan.footballsquadmanagerbp2.service.AlertService;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class PlayerDetailsController {
    AlertService alertService = new AlertService();
    PlayerService playerService = new PlayerService();

    // Method to get Strings, Integers or Values from Textfields or Comboboxes.
    public boolean ValidateAndSave(int playerId,
                                   TextField firstName,
                                   TextField lastName,
                                   TextField age,
                                   ComboBox<String> prefFoot,
                                   TextField shirtNumber,
                                   ComboBox<String> status,
                                   ComboBox<String> favPos,
                                   TextField otherPos) {

        String txtFirstName = firstName.getText();
        String txtLastName = lastName.getText();
        int intAge = Integer.parseInt(age.getText());
        String txtPrefFoot = prefFoot.getValue();
        int intShirtNumber = Integer.parseInt(shirtNumber.getText());
        String txtStatus = status.getValue();
        String txtFavPos = favPos.getValue();
        String txtOtherPos = otherPos.getText();

        //Takes each position split by comma's and puts it into an array, and converts it into a stream for processing.
        List<String> positions = Arrays.stream(txtOtherPos.split(","))
                //Trims all the whitespace.
                .map(String::trim)
                //Filters out any empty inputs
                .filter(s -> !s.isEmpty())
                //Puts them back into a List
                .collect(Collectors.toList());

        // TODO fix!
        // Returns alert if there is any in the validation.
        String alertString = playerService.ValidatePlayerForm(txtFirstName, txtLastName, intAge, txtPrefFoot, intShirtNumber, txtStatus, txtFavPos, positions);

        if (!alertString.isEmpty()) {
            alertService.getAlert(alertString);
            return false;
        }

        // If playerId = 0, this means that there is no id yet, so insert the player.
        if (playerId == 0) {
            playerService.insertPlayer(txtFirstName, txtLastName, intAge, txtPrefFoot, intShirtNumber, txtStatus, txtFavPos, positions);
        } else {
            // If there is one, pass it so that the existing player can be mutated.
            playerService.editPlayer(playerId, txtFirstName, txtLastName, intAge, txtPrefFoot, intShirtNumber, txtStatus, txtFavPos, positions);
        }
        return true;
    }

    // Navigating to the Data access objects.
    public String getFavPosColumn(int playerId) {
        PositionDAO positionDAO = new PositionDAO();
        return positionDAO.getPlayerPositions(playerId, 5);
    }
    public String getOtherPosColumn(int playerId) {
        PositionDAO positionDAO = new PositionDAO();
        return positionDAO.getPlayerPositions(playerId, 3);
    }
}
