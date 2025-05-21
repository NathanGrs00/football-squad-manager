package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

// This class is used to create a player object.
public class Player {
    // The variables are private to ensure encapsulation.
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

    // Second is for specific attributes into a Model Object.
    public Player(int playerId, String playerFirstName, String playerLastName, int playerAge, String playerPrefFoot, int playerShirtNumber, String playerStatus) {
        this.playerId = playerId;
        this.playerFirstName = playerFirstName;
        this.playerLastName = playerLastName;
        this.playerAge = playerAge;
        this.playerPrefFoot = playerPrefFoot;
        this.playerShirtNumber = playerShirtNumber;
        this.playerStatus = playerStatus;
    }

    // Getters for the variables.
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

    // toString method to display the player object in a readable format.
    @Override
    public String toString() {
        return playerFirstName + " " + playerLastName;
    }

    // Determining if objects are equal.
    // This is important to know if two players are the same.
    @Override
    public boolean equals(Object obj) {
        // Check if the object is the same instance.
        if (this == obj) return true;
        // Check if the object is null or of a different class, if so return false.
        if (obj == null || getClass() != obj.getClass()) return false;
        // cast the object to a Player object.
        Player player = (Player) obj;
        // return true if the playerId is the same, false if not.
        return this.playerId == player.playerId;
    }

    // When storing objects in hashMaps, they must have the same hashcode to avoid bugs.
    @Override
    public int hashCode() {
        // Use the playerId to create a hashcode.
        return Objects.hash(playerId);
    }
}
