package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Captain extends Player {

    public Captain(ResultSet rs) throws SQLException {
        super(rs);
    }

    public Captain(int playerId, String playerFirstName, String playerLastName, int playerAge, String playerPrefFoot, int playerShirtNumber, String playerStatus) {
        super(playerId, playerFirstName, playerLastName, playerAge, playerPrefFoot, playerShirtNumber, playerStatus);
    }
}
