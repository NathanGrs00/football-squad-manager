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

// Controller that handles the validation.
public class PlayerDetailsController {
    // Creating instances of the services and DAO.
    private final AlertService alertService = new AlertService();
    private final PlayerService playerService = new PlayerService();
    private final PositionDAO positionDAO = new PositionDAO();

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
        // Getting the values from the text fields and combo boxes.
        // using trim() to remove any leading or trailing whitespace.
        String txtFirstName = firstName.getText().trim();
        String txtLastName = lastName.getText().trim();
        String txtAge = age.getText().trim();
        String txtPrefFoot = prefFoot.getValue();
        // Taking the first letter of the foot, because of database constraints.
        String firstLetterFoot = txtPrefFoot.substring(0, 1);
        String txtShirtNumber = shirtNumber.getText().trim();
        String txtStatus = status.getValue();
        String txtFavPos = favPos.getValue();
        String txtOtherPos = otherPos.getText().trim();

        //Check if any of the names is empty.
        if (txtFirstName.isEmpty() || txtLastName.isEmpty()) {
            // show alert if any of the names is empty.
            alertService.getAlert("Please enter a valid name");
            return null;
        }

        // Check if age is empty.
        if (txtAge.isEmpty()) {
            // show alert if age is empty.
            alertService.getAlert("Age cannot be empty!");
            return null;
        }

        // check if shirt number is empty.
        if (txtShirtNumber.isEmpty()) {
            // show alert if shirt number is empty.
            alertService.getAlert("Shirt number cannot be empty!");
            return null;
        }

        int intAge;
        int intShirtNumber;
        try {
            // Parse the age and shirt number to integers.
            // This is needed because the input fields are strings.
            intAge = Integer.parseInt(txtAge);
            intShirtNumber = Integer.parseInt(txtShirtNumber);
            // If it can't be parsed, it will throw an exception.
        } catch (NumberFormatException e) {
            // Show alert.
            alertService.getAlert("Age and Shirt Number must be valid numbers!");
            return null;
        }

        // Check if the preferred foot combobox is empty, or the status or favPos fields are empty.
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

        // This is the string that gets returned if the validation fails.
        String alertString = playerService.ValidatePlayerForm(
                txtFirstName,
                txtLastName,
                intAge,
                intShirtNumber,
                positions
        );

        // If the alertString is not empty, it means that the validation failed.
        if (!alertString.isEmpty()) {
            // Show alert with the error message.
            alertService.getAlert(alertString);
            return null;
        }

        // Setting player to either a new player or captain, or a new player or captain with the existing id.
        Player player;
        // If the playerId is 0, it means that we are creating a new player.
        if (playerId == 0) {
            // Check if the player is a captain or not.
            if (shouldBeCaptain(captainCheckbox)) {
                // Make a new captain.
                player = new Captain(0,
                        txtFirstName,
                        txtLastName,
                        intAge,
                        firstLetterFoot,
                        intShirtNumber,
                        txtStatus
                );
            } else {
                // Otherwise, make a new player.
                player = new Player(0,
                        txtFirstName,
                        txtLastName,
                        intAge,
                        firstLetterFoot,
                        intShirtNumber,
                        txtStatus
                );
            }
        } else {
            // If the playerId is not 0, it means that we are editing an existing player.
            // Check if the player is a captain or not.
            if (shouldBeCaptain(captainCheckbox)) {
                // Make a new captain.
                player = new Captain(playerId, txtFirstName, txtLastName, intAge, firstLetterFoot, intShirtNumber, txtStatus);
            } else {
                // Otherwise, make a new player.
                player = new Player(playerId, txtFirstName, txtLastName, intAge, firstLetterFoot, intShirtNumber, txtStatus);
            }
        }
        // return the result, which is either a new player or an existing player.
        return player;
    }

    // Navigating to the Data access objects, and calling the getPlayerPositions method,
    // for both favorite and other positions. This is determined by the integer 5 or 3.
    public String getFavPosColumn(int playerId) {
        return positionDAO.getPlayerPositions(playerId, 5);
    }
    public String getOtherPosColumn(int playerId) {
        return positionDAO.getPlayerPositions(playerId, 3);
    }

    // Checking if checkbox is selected. returning true or false.
    private boolean shouldBeCaptain(CheckBox captainCheckbox) {
        return captainCheckbox.isSelected();
    }

    // Formatting position details for adding new player.
    public void insertPlayer(Player player, ComboBox<String> favPos, TextField otherPos) {
        // Getting the values from the text fields and combo boxes.
        // using trim() to remove any leading or trailing whitespace.
        // getValue() is used for the comboboxes, and getText() for the text fields.
        String txtFavPos = favPos.getValue();
        String txtOtherPos = otherPos.getText().trim();
        List<String> positions = parsePositions(txtOtherPos);
        // Calling the insertPlayer method from the playerService class.
        // the player object is passed in, along with the favorite position and other positions.
        // This is because those fields are not in the player object.
        playerService.insertPlayer(player, txtFavPos, positions);
    }

    // Formatting details for editing a player.
    public void updatePlayer(Player player, ComboBox<String> favPos, TextField otherPos) {
        // Getting the values from the text fields and combo boxes.
        String txtFavPos = favPos.getValue();
        String txtOtherPos = otherPos.getText().trim();
        List<String> positions = parsePositions(txtOtherPos);
        // Calling the editPlayer method from the playerService class.
        playerService.editPlayer(player, txtFavPos, positions);
    }

    // Parsing other positions string into right format for inserting.
    // This is needed because the other positions is a List of strings,
    // and the input is a string.
    private List<String> parsePositions(String positionText) {
        // Splitting the string by commas.
        return Arrays.stream(positionText.split(","))
                // Trimming all the whitespace.
                .map(String::trim)
                // Filtering out any empty inputs.
                .filter(s -> !s.isEmpty())
                // Putting them back into a List.
                .collect(Collectors.toList());
    }
}
