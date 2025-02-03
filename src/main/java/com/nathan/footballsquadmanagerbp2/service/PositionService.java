package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionService {
    private final PositionDAO positionDAO;

    public PositionService() {
        positionDAO = new PositionDAO();
    }

    public ArrayList<Position> getPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        ResultSet allPositions = positionDAO.getAllPositions();

        try {
            while (allPositions.next()) {
                positions.add(new Position(allPositions));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }

    public void setPlayerBestPosition(int playerId, String favPos) {
        Position position = positionDAO.getPosition(favPos);

        positionDAO.addPositionPlayerLink(playerId, position.getPositionId(), 5);
    }

    public void setOtherPositions(int playerId, List<String> positions) {
        for (String position : positions) {
            Position positionModel = positionDAO.getPosition(position);
            if (positionModel != null) {
                positionDAO.addPositionPlayerLink(playerId, positionModel.getPositionId(), 3);
            }
        }
    }

    public boolean checkIfPositionExists(List<String> positions) {
        if (positions.isEmpty()) {
            return true;
        }

        for (String position : positions) {
            if (positionDAO.getPosition(position) == null){
                return false;
            }
        }
        return true;
    }
}
