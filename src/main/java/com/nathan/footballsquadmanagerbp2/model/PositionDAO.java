package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.*;

public class PositionDAO {
    private Connection conn;

    public PositionDAO() {
        try {
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllPositions() {
        ResultSet positions;
        String query = "SELECT * FROM position";
        try {
            Statement stmt = conn.createStatement();
            positions = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }

    public Position getFavPosition(String positionName) {
        String query = "SELECT * FROM position WHERE abbreviation = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, positionName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Position(rs.getInt("id"), rs.getString("name"), rs.getString("abbreviation"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addPositionPlayerLink (int playerId, int positionId, int proficiency) {
        String query = "INSERT INTO player_position VALUES (?, ?, ?)";
        System.out.println(playerId);
        System.out.println(positionId);
        System.out.println(proficiency);

        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, positionId);
            pstmt.setInt(3, proficiency);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
