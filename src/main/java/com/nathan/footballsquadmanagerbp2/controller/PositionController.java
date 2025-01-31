package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.service.PositionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class PositionController {
    PositionService positionService = new PositionService();

    public ObservableList<String> getAllPositionNames() {
        ArrayList<Position> positions = positionService.getPositions();
        ObservableList<String> positionNames = FXCollections.observableArrayList();

        // TODO: make positionNames a list of Position.getAbbreviation() in a forloop.
        for (Position position : positions) {
            positionNames.add(position.getPositionAbreviation());
        }

        return positionNames;
    }

    public void getFavPos() {

    }
}
