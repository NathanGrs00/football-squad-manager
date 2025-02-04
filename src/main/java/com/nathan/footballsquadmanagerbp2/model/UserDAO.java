package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final Connection conn;

    // Constructor that calls an instance of the connection.
    public UserDAO() {
        try {
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public User getUser(String username) {
        String query = "SELECT * FROM user WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
