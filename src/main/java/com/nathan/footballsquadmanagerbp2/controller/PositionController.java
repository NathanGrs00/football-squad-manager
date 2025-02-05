package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;
import com.nathan.footballsquadmanagerbp2.service.PositionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.ArrayList;

public class PositionController {
    PositionService positionService = new PositionService();
    PositionDAO positionDAO = new PositionDAO();

    // Getting an observable list for the combobox from an ArrayList of all the position objects.
    public ObservableList<String> getAllPositionNames() {
        ResultSet allPositions = positionDAO.getAllPositions();
        ArrayList<Position> positions = positionService.getPositions(allPositions);
        ObservableList<String> positionNames = FXCollections.observableArrayList();

        for (Position position : positions) {
            positionNames.add(position.getPositionAbreviation());
        }

        return positionNames;
    }
}
