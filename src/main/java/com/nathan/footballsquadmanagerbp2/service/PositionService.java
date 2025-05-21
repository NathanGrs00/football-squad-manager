package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// PositionService class is used to handle the logic of the Position model.
public class PositionService {
    // PositionDAO is used to access the database.
    private final PositionDAO positionDAO;

    // Constructor for initializing the database class.
    public PositionService() {
        positionDAO = new PositionDAO();
    }

    // Taking a ResultSet and making an Arraylist of Position models.
    public ArrayList<Position> getPositions(ResultSet resultSet) {
        // Empty list to store the positions.
        ArrayList<Position> positions = new ArrayList<>();

        try {
            // Looping through the ResultSet and adding each position to the list.
            while (resultSet.next()) {
                // Creating a new Position object and adding it to the list.
                positions.add(new Position(resultSet));
            }
        } catch (SQLException e) {
            // If there is an error, print the stack trace and throw a runtime exception.
            throw new RuntimeException(e);
        }
        // Return the list of positions.
        return positions;
    }

    // Separate method to get the ResultSet, because in PlayerController I use getPositions() with an arraylist.
    public ArrayList<Position> getPositionsFromFormationId(int id) {
        // Getting the ResultSet from the PositionDAO method.
        // The ResultSet contains all the positions from the formation with the given id.
        ResultSet resultSet = positionDAO.getAllPositionsFromFormation(id);
        // returning the list of positions.
        // getPositions() method is used to convert the ResultSet to a list of Position objects.
        return getPositions(resultSet);
    }

    // Setting the players best position. 5 for proficiency will be the highest.
    public void setPlayerBestPosition(int playerId, String favPos) {
        // Getting the position from the database.
        Position position = positionDAO.getPosition(favPos);
        // Link the player with the position in the database, with a proficiency of 5. (best)
        positionDAO.addPositionPlayerLink(playerId, position.getPositionId(), 5);
    }

    // Setting the players other viable positions, with a proficiency of 3.
    public void setOtherPositions(int playerId, List<String> positions) {
        // Loop through the list of positions and add each one to the database.
        for (String position : positions) {
            // Getting the position from the database.
            Position positionModel = positionDAO.getPosition(position);
            // if the position is not null, link the player with the position in the database.
            if (positionModel != null) {
                // Link the player with the position in the database, with a proficiency of 3. (other)
                positionDAO.addPositionPlayerLink(playerId, positionModel.getPositionId(), 3);
            }
        }
    }

    // Checking if given positions exist in the database.
    public boolean checkIfPositionExists(List<String> positions) {
        // Does not give an error if there are no other viable positions.
        if (positions.isEmpty()) {
            return true;
        }

        // For each position check if there is a record in the database file, if not return false.
        for (String position : positions) {
            // Getting the position from the database and checking if it is null.
            if (positionDAO.getPosition(position) == null) {
                // If the position is null, it means it does not exist in the database.
                return false;
            }
            // If the position is not null, it means it exists in the database.
        }
        // If all positions exist in the database, return true.
        return true;
    }

    // Method to edit the player's best position.
    public void editPlayerBestPosition(int id, String favPos) {
        // List of a single string, so that updatePlayerPosition can be used for both cases.
        positionDAO.updatePlayerPosition(id, List.of(favPos), 5);
    }

    // Method to edit the player's other positions.
    public void editOtherPositions(int id, List<String> positions) {
        // updatePlayerPosition method is used to update the player's positions in the database.
        positionDAO.updatePlayerPosition(id, positions, 3);
    }
}
