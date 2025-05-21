package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;
import com.nathan.footballsquadmanagerbp2.service.PositionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PositionController {
    // Service and DAO classes are instantiated to be used in the controller.
    PositionService positionService = new PositionService();
    PositionDAO positionDAO = new PositionDAO();

    // Getting an observable list for the combobox from an ArrayList of all the position objects.
    public ObservableList<String> getAllPositionNames() {
        // Get all positions from the database using the DAO
        ResultSet allPositions = positionDAO.getAllPositions();
        // Turn the result into an ArrayList of Position objects
        ArrayList<Position> positions = positionService.getPositions(allPositions);
        // Create an observable list for the combobox
        ObservableList<String> positionNames = FXCollections.observableArrayList();

        // Loop through the list
        for (Position position : positions) {
            // for each position, add the name to the observable list
            positionNames.add(position.getPositionAbreviation());
        }

        // Return the observable list
        return positionNames;
    }

    // Get a list of all positions from the service class
    public List<Position> getPositionsList(int formationId) {
        return positionService.getPositionsFromFormationId(formationId);
    }
}
