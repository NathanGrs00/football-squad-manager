package com.nathan.footballsquadmanagerbp2.model;

public class SelectionDetail {
    private int selectionId;
    private int playerId;
    private int positionId;

    public SelectionDetail(int selectionId, int playerId, int positionId) {
        this.selectionId = selectionId;
        this.playerId = playerId;
        this.positionId = positionId;
    }

    public int getSelectionId() {
        return selectionId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setSelectionId(int selectionId) {
        this.selectionId = selectionId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }
}
