package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionService {
    private final PositionDAO positionDAO;

    // Constructor for initializing the database class.
    public PositionService() {
        positionDAO = new PositionDAO();
    }

    // Taking a ResultSet and making an Arraylist of Position models.
    public ArrayList<Position> getPositions(ResultSet resultSet) {
        ArrayList<Position> positions = new ArrayList<>();

        try {
            while (resultSet.next()) {
                positions.add(new Position(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }

    public ArrayList<Position> getPositionsFromFormationId(int id) {
        ResultSet resultSet = positionDAO.getAllPositionsFromFormation(id);
        return getPositions(resultSet);
    }

    // Setting the players best position. 5 for proficiency will be the highest.
    public void setPlayerBestPosition(int playerId, String favPos) {
        Position position = positionDAO.getPosition(favPos);

        positionDAO.addPositionPlayerLink(playerId, position.getPositionId(), 5);
    }

    // Setting the players other viable positions, with a proficiency of 3.
    public void setOtherPositions(int playerId, List<String> positions) {
        for (String position : positions) {
            Position positionModel = positionDAO.getPosition(position);
            if (positionModel != null) {
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
            if (positionDAO.getPosition(position) == null) {
                return false;
            }
        }
        return true;
    }

    // Sending a List of a single string, so that updatePlayerPosition can be used for both cases.
    public void editPlayerBestPosition(int id, String favPos) {
        positionDAO.updatePlayerPosition(id, List.of(favPos), 5);
    }

    public void editOtherPositions(int id, List<String> positions) {
        positionDAO.updatePlayerPosition(id, positions, 3);
    }
}
