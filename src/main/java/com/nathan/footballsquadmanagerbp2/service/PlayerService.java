package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Captain;
import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.PlayerDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// This class is responsible for managing player data and operations.
public class PlayerService {
    // Private variables to hold the PlayerDAO and PositionService instances.
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
                                      int intShirtNumber,
                                      List<String> positions) {
        // Making an empty string to hold the error message.
        String alertString = "";

        // Validating the input.
        if (intShirtNumber < 0 || intShirtNumber > 99) {
            // Gives a fitting error.
            alertString = "Shirt number must be between 0 and 99";
        } else if (txtFirstName.length() > 15) {
            alertString = "First name must be less than 16 characters";
        } else if (txtLastName.length() > 20) {
            alertString = "Last name must be less than 21 characters";
        } else if (intAge < 15 || intAge > 65) {
            alertString = "Age must be between 15 and 65";
            // check if position exists in the database.
        } else if (!positionService.checkIfPositionExists(positions)) {
            // Gives an error if any position in the input does not exist.
            alertString = "Please enter the other positions in this format: 'RWB, CDM, LB' etc.";
        }
        // Returns the error, or an empty string if all checks are good.
        return alertString;
    }

    // Inserting and editing players in the database.
    public void insertPlayer(Player player,
                             String favPos,
                             List<String> positions) {


        boolean isCaptain = (player instanceof Captain);
        // Inserting the player in the database and returning the generated id.
        int generatedId = playerDAO.insertPlayer(player, isCaptain);
        // Using the generated id to insert data in player_position table.
        positionService.setPlayerBestPosition(generatedId, favPos);

        positions.removeIf(pos -> pos.equalsIgnoreCase(favPos));
        positionService.setOtherPositions(generatedId, positions);
    }

    public void editPlayer(Player player,
                           String favPos,
                           List<String> positions) {

        // Checking if the player is a captain.
        // If the player is a captain, we need to set the is_captain field to true.
        boolean isCaptain = (player instanceof Captain);

        // Passing new values to the database method to edit the player.
        playerDAO.editPlayer(player.getPlayerId(), player, isCaptain);
        // Also passing the id with the new player positions. favorite and other positions.
        positionService.editPlayerBestPosition(player.getPlayerId(), favPos);
        positionService.editOtherPositions(player.getPlayerId(), positions);
    }

    // Deleting a player from the database.
    public void deletePlayer(Player player) {
        // Getting the player id from the player object.
        int id = player.getPlayerId();
        // Deleting the player in the service class.
        playerDAO.deletePlayer(id);
    }

    // Turning a Resultset into an ArrayList.
    public ArrayList<Player> getPlayers() {
        // Make a new ArrayList to hold the players.
        ArrayList<Player> players = new ArrayList<>();
        // Getting all players from the database.
        ResultSet allPlayers = playerDAO.getAllPlayers();

        try {
            // Looping through the ResultSet and creating Player objects.
            while (allPlayers.next()) {
                // Getting the is_captain field from the ResultSet.
                boolean isCaptain = allPlayers.getBoolean("is_captain");
                // player object
                Player player;
                // If the player is a captain, create a Captain object.
                if (isCaptain) {
                    player = new Captain(allPlayers);
                } else {
                    // If the player is not a captain, create a regular Player object.
                    player = new Player(allPlayers);
                }
                // adding the player to the ArrayList.
                players.add(player);
            }
        } catch(SQLException e) {
            // If there is an error, throw a runtime exception.
            throw new RuntimeException(e);
        }
        // Returning the ArrayList of players.
        return players;
    }

    // Getting player by id.
    public Player getPlayerById(int id) {
        // For all players in the getPlayers List,
        for (Player player : getPlayers()) {
            // If the player id is equal to the id we are looking for,
            if (player.getPlayerId() == id) {
                // Return the player.
                return player;
            }
        }
        // If the player is not found, return null.
        return null;
    }
}
