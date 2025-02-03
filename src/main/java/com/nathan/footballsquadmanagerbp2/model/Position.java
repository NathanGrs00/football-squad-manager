package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Position {
    private int positionId;
    private String positionName;
    private String positionAbreviation;


    // First constructor is for the data in SQL, to a model.
    public Position(ResultSet rs) throws SQLException {
        positionId = rs.getInt("id");
        positionName = rs.getString("name");
        positionAbreviation = rs.getString("abbreviation");
    }

    // Second is for input into Model.
    public Position(int positionId, String positionName, String positionAbreviation) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.positionAbreviation = positionAbreviation;
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

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setPositionAbreviation(String positionAbreviation) {
        this.positionAbreviation = positionAbreviation;
    }
}
