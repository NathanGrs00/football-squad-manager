package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.*;

public class PlayerDAO {
    private final Connection conn;

    public PlayerDAO() {
        try {
            DBConnector db = new DBConnector();
            conn = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insertPlayer(Player player) {
        String query = "INSERT INTO player VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, player.getPlayerId());
            pstmt.setString(2, player.getPlayerFirstName());
            pstmt.setString(3, player.getPlayerLastName());
            pstmt.setInt(4, player.getPlayerAge());
            pstmt.setString(5, player.getPlayerPrefFoot());
            pstmt.setInt(6, player.getPlayerShirtNumber());
            pstmt.setString(7, player.getPlayerStatus());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAllPlayers() {
        ResultSet players;
        String query = "SELECT * FROM player";
        try {
            Statement stmt = conn.createStatement();
            players = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return players;
    }
}
