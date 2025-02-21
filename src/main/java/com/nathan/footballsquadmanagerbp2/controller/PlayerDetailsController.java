package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Captain;
import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;
import com.nathan.footballsquadmanagerbp2.service.AlertService;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class PlayerDetailsController {
    AlertService alertService = new AlertService();
    PlayerService playerService = new PlayerService();

    // Method to get Strings, Integers or Values from Textfields or Comboboxes.
    public Player ValidateInputs(int playerId,
                                  TextField firstName,
                                  TextField lastName,
                                  TextField age,
                                  ComboBox<String> prefFoot,
                                  TextField shirtNumber,
                                  ComboBox<String> status,
                                  ComboBox<String> favPos,
                                  TextField otherPos,
                                  CheckBox captainCheckbox) {

        String txtFirstName = firstName.getText().trim();
        String txtLastName = lastName.getText().trim();
        String txtAge = age.getText().trim();
        String txtPrefFoot = prefFoot.getValue();
        // Taking the first letter of the foot, database constraints.
        String firstLetterFoot = txtPrefFoot.substring(0, 1);
        String txtShirtNumber = shirtNumber.getText().trim();
        String txtStatus = status.getValue();
        String txtFavPos = favPos.getValue();
        String txtOtherPos = otherPos.getText().trim();

        //Check if name is empty.
        if (txtFirstName.isEmpty() || txtLastName.isEmpty()) {
            alertService.getAlert("Please enter a valid name");
            return null;
        }

        // Check if age is empty.
        if (txtAge.isEmpty()) {
            alertService.getAlert("Age cannot be empty!");
            return null;
        }

        // check if shirt number is empty.
        if (txtShirtNumber.isEmpty()) {
            alertService.getAlert("Shirt number cannot be empty!");
            return null;
        }

        int intAge;
        int intShirtNumber;
        try {
            intAge = Integer.parseInt(txtAge);
            intShirtNumber = Integer.parseInt(txtShirtNumber);
        } catch (NumberFormatException e) {
            alertService.getAlert("Age and Shirt Number must be valid numbers!");
            return null;
        }

        if (prefFoot.getValue() == null || txtStatus == null || txtFavPos == null) {
            alertService.getAlert("All dropdowns must be selected!");
            return null;
        }

        //Takes each position split by comma's and puts it into an array, and converts it into a stream for processing.
        List<String> positions = Arrays.stream(txtOtherPos.split(","))
                //Trims all the whitespace.
                .map(String::trim)
                //Filters out any empty inputs
                .filter(s -> !s.isEmpty())
                //Puts them back into a List
                .collect(Collectors.toList());

        // Returns alert if there is any in the validation.
        String alertString = playerService.ValidatePlayerForm(txtFirstName, txtLastName, intAge, intShirtNumber, positions);

        if (!alertString.isEmpty()) {
            alertService.getAlert(alertString);
            return null;
        }

        Player player;
        if (playerId == 0) {
            if (shouldBeCaptain(captainCheckbox)) {
                player = new Captain(0, txtFirstName, txtLastName, intAge, firstLetterFoot, intShirtNumber, txtStatus);
            } else {
                player = new Player(0, txtFirstName, txtLastName, intAge, firstLetterFoot, intShirtNumber, txtStatus);
            }
        } else {
            if (shouldBeCaptain(captainCheckbox)) {
                player = new Captain(playerId, txtFirstName, txtLastName, intAge, firstLetterFoot, intShirtNumber, txtStatus);
            } else {
                player = new Player(playerId, txtFirstName, txtLastName, intAge, firstLetterFoot, intShirtNumber, txtStatus);
            }
        }
        return player;
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

    private boolean shouldBeCaptain(CheckBox captainCheckbox) {
        return captainCheckbox.isSelected();
    }

    public void insertPlayer(Player player, ComboBox<String> favPos, TextField otherPos) {
        String txtFavPos = favPos.getValue();
        String txtOtherPos = otherPos.getText().trim();
        List<String> positions = Arrays.stream(txtOtherPos.split(","))
                //Trims all the whitespace.
                .map(String::trim)
                //Filters out any empty inputs
                .filter(s -> !s.isEmpty())
                //Puts them back into a List
                .collect(Collectors.toList());
        playerService.insertPlayer(player, txtFavPos, positions);
    }

    public void updatePlayer(Player player, ComboBox<String> favPos, TextField otherPos) {
        String txtFavPos = favPos.getValue();
        String txtOtherPos = otherPos.getText().trim();
        List<String> positions = Arrays.stream(txtOtherPos.split(","))
                //Trims all the whitespace.
                .map(String::trim)
                //Filters out any empty inputs
                .filter(s -> !s.isEmpty())
                //Puts them back into a List
                .collect(Collectors.toList());
        playerService.editPlayer(player, txtFavPos, positions);
    }
}
