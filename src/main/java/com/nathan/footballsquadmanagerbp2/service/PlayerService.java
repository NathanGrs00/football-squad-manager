package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.PlayerDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    // Private variables.
    private final PlayerDAO playerDAO;
    private final PositionService positionService;

    // Constructor to initialize the variables.
    public PlayerService() {
        playerDAO = new PlayerDAO();
        positionService = new PositionService();
    }

    // Validating parsed input method.
    public String ValidatePlayerForm(String txtFirstName,
                                      String txtLastName,
                                      int intAge,
                                      String txtPrefFoot,
                                      int intShirtNumber,
                                      String txtStatus,
                                      String txtFavPos,
                                      List<String> positions) {

        String alertString = "";

        //Checking if comboboxes are empty;
        if (txtPrefFoot == null || txtStatus == null || txtFavPos == null) {
            alertString = "Please fill in the preferred foot and the status of the player";
        } else if (intShirtNumber < 0 || intShirtNumber > 99) {
            alertString = "Shirt number must be between 0 and 99";
        } else if (txtFirstName.length() > 15) {
            alertString = "First name must be less than 16 characters";
        } else if (txtLastName.length() > 20) {
            alertString = "Last name must be less than 21 characters";
        } else if (intAge < 15 || intAge > 65) {
            alertString = "Age must be between 15 and 65";
        } else if (!positionService.checkIfPositionExists(positions)) {
            // Gives an error if any position in the input does not exist.
            alertString = "Please enter the other positions in this format: 'RWB, CDM, LB' etc.";
        }
        // Returns the error, or non if all checks are good.
        return alertString;
    }

    public void insertPlayer(String firstName, String lastName, int age, String prefFoot, int shirtNumber, String status, String favPos, List<String> positions) {
        int id = 0;
        // Taking the first letter of the foot, database constraints.
        String firstLetterFoot = prefFoot.substring(0, 1);

        // Saving input as a player model.
        Player player = new Player(id, firstName, lastName, age, firstLetterFoot, shirtNumber, status);

        // Inserting the player in the database and returning the generated id.
        int generatedId = playerDAO.insertPlayer(player);
        // Using the generated id to insert data in player_position table.
        positionService.setPlayerBestPosition(generatedId, favPos);
        positionService.setOtherPositions(generatedId, positions);
    }

    public void editPlayer(int id, String firstName, String lastName, int age, String prefFoot, int shirtNumber, String status, String favPos, List<String> positions) {
        String firstLetterFoot = prefFoot.substring(0, 1);

        // Passing new values to the database method.
        Player player = new Player(id, firstName, lastName, age, firstLetterFoot, shirtNumber, status);
        playerDAO.editPlayer(id, player);
        // Also passing the id with the new player positions.
        positionService.editPlayerBestPosition(id, favPos);
        positionService.editOtherPositions(id, positions);
    }

    public void deletePlayer(Player player) {
        int id = player.getPlayerId();
        playerDAO.deletePlayer(id);
    }

    // Turning a Resultset into an ArrayList.
    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        ResultSet allPlayers = playerDAO.getAllPlayers();

        try {
            while (allPlayers.next()) {
                players.add(new Player(allPlayers));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return players;
    }
}
