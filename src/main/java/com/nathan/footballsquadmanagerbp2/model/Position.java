package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Position {
    private final int positionId;
    private final String positionName;
    private final String positionAbreviation;
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
