package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;

// This class is used to represent a position in the football squad manager application.
// like "Goalkeeper", "Defender", "Midfielder", "Forward" etc.
public class Position {
    // The fields represent the properties of a position.
    private final int positionId;
    private final String positionName;
    private final String positionAbreviation;
    // The x and y positions are used to represent the position on a football field.
    // We are using a 2D grid to represent the field.
    private final int xPosition;
    private final int yPosition;

    // First constructor is for the data in SQL, to a model.
    public Position(ResultSet rs) throws SQLException {
        positionId = rs.getInt("id");
        positionName = rs.getString("name");
        positionAbreviation = rs.getString("abbreviation");
        xPosition = rs.getInt("xposition");
        yPosition = rs.getInt("yposition");
    }

    // Second is for input into Model.
    public Position(int positionId, String positionName, String positionAbreviation, int xPosition, int yPosition) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.positionAbreviation = positionAbreviation;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    // Getters for the fields.
    public int getPositionId() {
        return positionId;
    }

    // If I ever make a way to view player details in the selectionbuilder, I would use this to display the full name.
    public String getPositionName() {
        return positionName;
    }

    public String getPositionAbreviation() {
        return positionAbreviation;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }
}
