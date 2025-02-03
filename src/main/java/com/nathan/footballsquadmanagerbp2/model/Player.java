package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Player {
    private int playerId;
    private String playerFirstName;
    private String playerLastName;
    private int playerAge;
    private String playerPrefFoot;
    private int playerShirtNumber;
    private String playerStatus;

    // First constructor is for the data in SQL, to a model.
    public Player(ResultSet rs) throws SQLException {
        this.playerId = rs.getInt("id");
        this.playerFirstName = rs.getString("first_name");
        this.playerLastName = rs.getString("last_name");
        this.playerAge = rs.getInt("age");
        this.playerPrefFoot = rs.getString("pref_foot");
        this.playerShirtNumber = rs.getInt("playing_number");
        this.playerStatus = rs.getString("status");
    }

    // Second is for Data into Model.
    public Player(int playerId, String playerFirstName, String playerLastName, int playerAge, String playerPrefFoot, int playerShirtNumber, String playerStatus) {
        this.playerId = playerId;
        this.playerFirstName = playerFirstName;
        this.playerLastName = playerLastName;
        this.playerAge = playerAge;
        this.playerPrefFoot = playerPrefFoot;
        this.playerShirtNumber = playerShirtNumber;
        this.playerStatus = playerStatus;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getPlayerFirstName() {
        return playerFirstName;
    }

    public String getPlayerLastName() {
        return playerLastName;
    }

    public int getPlayerAge() {
        return playerAge;
    }

    public String getPlayerPrefFoot() {
        return playerPrefFoot;
    }

    public int getPlayerShirtNumber() {
        return playerShirtNumber;
    }

    public String getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setPlayerFirstName(String playerFirstName) {
        this.playerFirstName = playerFirstName;
    }

    public void setPlayerLastName(String playerLastName) {
        this.playerLastName = playerLastName;
    }

    public void setPlayerAge(int playerAge) {
        this.playerAge = playerAge;
    }

    public void setPlayerPrefFoot(String playerPrefFoot) {
        this.playerPrefFoot = playerPrefFoot;
    }

    public void setPlayerShirtNumber(int playerShirtNumber) {
        this.playerShirtNumber = playerShirtNumber;
    }

    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }
}
