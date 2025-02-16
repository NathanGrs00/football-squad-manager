package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Player {
    private final int playerId;
    private final String playerFirstName;
    private final String playerLastName;
    private final int playerAge;
    private final String playerPrefFoot;
    private final int playerShirtNumber;
    private final String playerStatus;

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

    // Ensures player name can be used.
    @Override
    public String toString() {
        return playerFirstName + " " + playerLastName;
    }

    // Determining if objects are equal.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Player player = (Player) obj;
        return this.playerId == player.playerId;
    }

    // When storing objects in hashMaps, they must have the same hashcode to avoid bugs.
    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }
}
