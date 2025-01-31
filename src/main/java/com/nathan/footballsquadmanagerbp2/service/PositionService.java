package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PositionService {
    private PositionDAO positionDAO;

    public ArrayList<Player> getPositions() {
        ArrayList<Player> positions = new ArrayList<>();
        ResultSet allPositions = positionDAO.getAllPositions();

        try {
            while (allPositions.next()) {
                positions.add(new Player(allPositions));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }
}
