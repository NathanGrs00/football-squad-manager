package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;

// Captain class for the polymorphism checks.
public class Captain extends Player {

    // Constructor for creating a Captain object from a ResultSet.
    // This constructor is used when retrieving data from the database.
    public Captain(ResultSet rs) throws SQLException {
        super(rs);
    }

    // Constructor for creating a Captain object with specific attributes.
    public Captain(int playerId, String playerFirstName, String playerLastName, int playerAge, String playerPrefFoot, int playerShirtNumber, String playerStatus) {
        super(playerId, playerFirstName, playerLastName, playerAge, playerPrefFoot, playerShirtNumber, playerStatus);
    }
}
