package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        Position position = positionDAO.getFavPosition(favPos);

        positionDAO.addPositionPlayerLink(playerId, position.getPositionId(), 5);
    }
}
