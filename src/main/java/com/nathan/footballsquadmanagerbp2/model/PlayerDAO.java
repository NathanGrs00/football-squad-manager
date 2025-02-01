package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.*;

public class PlayerDAO {
    private Connection conn;

    public PlayerDAO() {
        try {
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int insertPlayer(Player player) {
        String query = "INSERT INTO player VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, player.getPlayerId());
            pstmt.setString(2, player.getPlayerFirstName());
            pstmt.setString(3, player.getPlayerLastName());
            pstmt.setInt(4, player.getPlayerAge());
            pstmt.setString(5, player.getPlayerPrefFoot());
            pstmt.setInt(6, player.getPlayerShirtNumber());
            pstmt.setString(7, player.getPlayerStatus());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if(rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public void deletePlayer(int playerId) {
        String deletePlayerPositionLink = "DELETE FROM player_position WHERE player_id = ?";
        String query = "DELETE FROM player WHERE id = ?";
        try (PreparedStatement pstmt1 = conn.prepareStatement(deletePlayerPositionLink);
             PreparedStatement pstmt2 = conn.prepareStatement(query)) {
            pstmt1.setInt(1, playerId);
            pstmt1.executeUpdate();
            pstmt2.setInt(1, playerId);
            pstmt2.executeUpdate();
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
