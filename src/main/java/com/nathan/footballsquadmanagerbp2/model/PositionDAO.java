package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.*;
import java.util.List;

// This class is responsible for all the queries related to the position table.
public class PositionDAO {
    // Singleton instance of the connection.
    private final Connection conn;

    // Constructor to get the same instance of the connection.
    public PositionDAO() {
        try {
            // Get the connection instance from DBConnector.
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e) {
            // Handle the exception if the connection fails.
            throw new RuntimeException(e);
        }
    }

    // Getting all the positions from the database.
    public ResultSet getAllPositions() {
        // Query to select all positions from the position table.
        String query = "SELECT * FROM position ORDER BY id ASC";
        try {
            // Prepare the statement and execute the query.
            return conn.prepareStatement(query).executeQuery();
        } catch (SQLException e) {
            // Handle the exception if the query fails.
            throw new RuntimeException(e);
        }
    }

    // Get a position from a position name (abbreviation).
    public Position getPosition(String positionName) {
        // Query to select a position based on the abbreviation.
        String query = "SELECT * FROM position WHERE abbreviation = ? LIMIT 1";

        // Prepare the statement and set the abbreviation parameter.
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the abbreviation parameter.
            pstmt.setString(1, positionName);

            // Execute the query and get the result set.
            try (ResultSet rs = pstmt.executeQuery()) {
                // If a result is found,
                if (rs.next()) {
                    // Create a new Position object and return it.
                    return new Position(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("abbreviation"),
                            rs.getInt("xposition"),
                            rs.getInt("yposition")
                    );
                }
            }
            // catch any SQL exceptions that occur during the process.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // If no result is found, return null.
        return null;
    }

    // Getting a player's position from the player_position table based on the proficiency.
    public String getPlayerPositions(int playerId, int proficiency) {
        // StringBuilder to store the positions.
        StringBuilder positions = new StringBuilder();
        // Query to select the abbreviation of positions based on player ID and proficiency.
        String query = "SELECT p.abbreviation FROM position p JOIN player_position pp ON pp.position_id = p.id " +
                       "WHERE pp.player_id = ? AND pp.proficiency = ?";
        // Prepare the statement and set the player ID and proficiency parameters.
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the player ID and proficiency parameters.
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, proficiency);
            // Execute the query and get the result set.
            try (ResultSet rs = pstmt.executeQuery()) {
                // Iterate through the result set
                while (rs.next()) {
                    // If the StringBuilder is not empty,
                    if (!positions.isEmpty()) {
                        // Append a comma and space if there are already positions.
                        positions.append(", ");
                    }
                    // Append the abbreviation of the position to the StringBuilder.
                    positions.append(rs.getString("abbreviation"));
                }
            }
            // catch any SQL exceptions that occur during the process.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Return the positions as a string.
        return positions.toString();
    }

    // adding the position_player entry after inserting player.
    public void addPositionPlayerLink (int playerId, int positionId, int proficiency) {
        // Query to insert a new entry into the player_position table.
        String query = "INSERT INTO player_position VALUES (?, ?, ?)";

        // Prepare the statement and set the player ID, position ID, and proficiency parameters.
        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, positionId);
            pstmt.setInt(3, proficiency);
            // Execute the update to insert the new entry.
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception if the query fails.
            throw new RuntimeException(e);
        }
    }

    // Add or update player position based on the proficiency if the user changes values.
    public void updatePlayerPosition(int playerId, List<String> positions, int proficiency) {
        // Delete existing positions first.
        String deleteQuery = "DELETE FROM player_position WHERE player_id = ? AND proficiency = ?";
        // Prepare the statement and set the player ID and proficiency parameters.
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
            // Set the player ID and proficiency parameters.
            deleteStmt.setInt(1, playerId);
            deleteStmt.setInt(2, proficiency);
            // Execute the update to delete existing positions.
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception if the query fails.
            throw new RuntimeException(e);
        }

        // Insert updated data.
        // Query to insert new entries into the player_position table.
        String insertQuery = "INSERT INTO player_position (player_id, position_id, proficiency) " +
                "VALUES (?, (SELECT id FROM position WHERE abbreviation = ?), ?)";
        // Prepare the statement and set the player ID, position name, and proficiency parameters.
        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            // Iterate through the list of positions.
            for (String positionName : positions) {
                // Set the player ID, position name, and proficiency parameters.
                insertStmt.setInt(1, playerId);
                insertStmt.setString(2, positionName);
                insertStmt.setInt(3, proficiency);
                // Execute the update to insert the new entry.
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            // Handle the exception if the query fails.
            throw new RuntimeException(e);
        }
    }

    // Get all positions query
    public ResultSet getAllPositionsFromFormation(int formationId) {
        // Query to select all positions from the position table based on formation ID.
        String query = "SELECT p.* FROM position p JOIN formation_position fp ON p.id = fp.position_id WHERE fp.formation_id = ?";
        try {
            // Prepare the statement and set the formation ID parameter.
            PreparedStatement pstmt = conn.prepareStatement(query);
            // Set the formation ID parameter.
            pstmt.setInt(1, formationId);
            // Execute the query and return the result set.
            return pstmt.executeQuery();
        } catch (SQLException e) {
            // Handle the exception if the query fails.
            throw new RuntimeException(e);
        }
    }

    // Get Proficiency query.
    public Integer getPlayerProficiency(int playerId, int positionId) {
        // Query to select the proficiency from the player_position table based on player ID and position ID.
        String query = "SELECT proficiency FROM player_position WHERE player_id = ? AND position_id = ?";

        // Prepare the statement and set the player ID and position ID parameters.
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the player ID and position ID parameters.
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, positionId);
            // Execute the query and get the result set.
            ResultSet rs = pstmt.executeQuery();

            // If a result is found,
            if (rs.next()) {
                // Return the proficiency value.
                return rs.getInt("proficiency");
            }
        } catch (SQLException e) {
            // Handle the exception if the query fails.
            throw new RuntimeException(e);
        }

        // If no result is found, return null.
        return null;
    }
}
