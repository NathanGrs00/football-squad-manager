package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Position {
    private int positionId;
    private String positionName;
    private String positionAbreviation;
    private int xPosition;
    private int yPosition;


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

    public String getPositionName() {
        return positionName;
    }

    public String getPositionAbreviation() {
        return positionAbreviation;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setPositionAbreviation(String positionAbreviation) {
        this.positionAbreviation = positionAbreviation;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }


}
