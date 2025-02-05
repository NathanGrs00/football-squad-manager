package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.*;
import java.util.List;

public class PositionDAO {
    private final Connection conn;

    // Constructor to get the same instance of the connection.
    public PositionDAO() {
        try {
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Getting all the positions from the database.
    public ResultSet getAllPositions() {
        String query = "SELECT * FROM position";
        try {
            return conn.prepareStatement(query).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Get a position from a position name (abbreviation).
    public Position getPosition(String positionName) {
        String query = "SELECT * FROM position WHERE abbreviation = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, positionName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Position(rs.getInt("id"), rs.getString("name"), rs.getString("abbreviation"), rs.getInt("xposition"), rs.getInt("yposition"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Getting a player's position from the player_position table based on the proficiency.
    public String getPlayerPositions(int playerId, int proficiency) {
        StringBuilder positions = new StringBuilder();
        String query = "SELECT p.abbreviation FROM position p JOIN player_position pp ON pp.position_id = p.id " +
                       "WHERE pp.player_id = ? AND pp.proficiency = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, proficiency);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (!positions.isEmpty()) {
                        positions.append(", ");
                    }
                    positions.append(rs.getString("abbreviation"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return positions.toString();
    }

    // adding the position_player entry after inserting player.
    public void addPositionPlayerLink (int playerId, int positionId, int proficiency) {
        String query = "INSERT INTO player_position VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, positionId);
            pstmt.setInt(3, proficiency);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Add or update player position based on the proficiency if the user changes values.
    public void updatePlayerPosition(int playerId, List<String> positions, int proficiency) {
        // Delete existing positions first.
        String deleteQuery = "DELETE FROM player_position WHERE player_id = ? AND proficiency = ?";
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, playerId);
            deleteStmt.setInt(2, proficiency);
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Insert updated data.
        String insertQuery = "INSERT INTO player_position (player_id, position_id, proficiency) " +
                "VALUES (?, (SELECT id FROM position WHERE abbreviation = ?), ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            for (String positionName : positions) {
                insertStmt.setInt(1, playerId);
                insertStmt.setString(2, positionName);
                insertStmt.setInt(3, proficiency);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllPositionsFromFormation(int formationId) {
        String query = "SELECT p.* FROM position p JOIN formation_position fp ON p.id = fp.position_id WHERE fp.formation_id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, formationId);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
