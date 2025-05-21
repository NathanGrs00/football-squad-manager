package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Class to handle user data access in the database.
public class UserDAO {
    // Connection to the database.
    private final Connection conn;

    // Constructor that calls an instance of the connection.
    public UserDAO() {
        try {
            // Get the connection from the DBConnector class.
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e){
            // Handle any SQL exceptions that may occur.
            throw new RuntimeException(e);
        }
    }

    // Get specific user by name.
    public User getUserByUsername(String username) {
        // SQL query to select user by name.
        String query = "SELECT * FROM user WHERE name = ?";
        // Prepare the statement to prevent SQL injection.
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the username parameter in the query.
            pstmt.setString(1, username);
            // Execute the query and get the result set.
            ResultSet rs = pstmt.executeQuery();
            // If a result is found,
            if (rs.next()) {
                // make a User object and return it.
                return mapUser(rs);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions that may occur.
            throw new RuntimeException(e);
        }
        // If no user is found, return null.
        return null;
    }

    // Get user by id.
    public User getUserById(int userId) {
        // SQL query to select user by id.
        String query = "SELECT * FROM user WHERE id = ?";
        // Prepare the statement to prevent SQL injection.
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the userId parameter in the query.
            pstmt.setInt(1, userId);
            // Execute the query and get the result set.
            ResultSet rs = pstmt.executeQuery();
            // If a result is found,
            if (rs.next()) {
                // make a User object and return it.
                return mapUser(rs);
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions that may occur.
            throw new RuntimeException(e);
        }
        // If no user is found, return null.
        return null;
    }

    // Method to avoid redundancy.
    private User mapUser(ResultSet rs) throws SQLException {
        // Create a new User object and set its properties from the result set.
        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getString("role")
        );
    }
}
