package com.nathan.footballsquadmanagerbp2.model;

public class Position {
    private int positionId;
    private String positionName;
    private String positionAbreviation;

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
