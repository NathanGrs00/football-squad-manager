package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// This class is responsible for managing the formation table in the database.
public class FormationDAO {
    // Connection to the database.
    private final Connection conn;

    // Constructor that initializes the connection to the database.
    public FormationDAO() {
        try {
            // Get the connection to the database.
            // This is a singleton class, so it will only be created once.
            conn = DBConnector.getInstance().getConnection();
            // if the connection is null, throw an exception.
        } catch (SQLException e) {
            // Print the stack trace and throw a runtime exception.
            throw new RuntimeException(e);
        }
    }

    // Get all formations query.
    public List<Formation> getAllFormations() {
        // Create a list to store the formations.
        List<Formation> formations = new ArrayList<>();
        // Create a resultSet to store the results of the query.
        ResultSet resultSet;
        // Create a query to get all formations from the database.
        String query = "SELECT * FROM formation";
        // Try making a statement and executing the query.
        try {
            // Create a statement.
            Statement stmt = conn.createStatement();
            // Execute the query and get the result set.
            resultSet = stmt.executeQuery(query);
            // Loop through the result set with a while loop.
            while (resultSet.next()) {
                // for each row in the result set, create a new formation object and add it to the list of formations.
                formations.add(new Formation(resultSet));
            }
            // Throw an exception if the result set is empty.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Return the list of formations.
        return formations;
    }

    // Get specific formation query
    public Formation getFormationById(int id) {
        // String to store the query.
        String query = "SELECT id, name FROM formation WHERE id = ?";
        // Create a prepared statement to prevent SQL injection.
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the id parameter in the query.
            stmt.setInt(1, id);
            // Execute the query and get the result set.
            ResultSet rs = stmt.executeQuery();
            // if there is a next row in the result set,
            if (rs.next()) {
                // create a new formation object and return it.
                return new Formation(rs.getInt("id"), rs.getString("name"));
            }
            // catch any exceptions and throw a runtime exception.
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // If there is no next row, return null.
        return null;
    }

    // Get the amount of formations by name, using GROUP BY.
    public List<String> getFormationCountsByName() {
        // Create a list to store the results.
        List<String> results = new ArrayList<>();
        // Create a query to get the count of formations by name.
        String query = "SELECT f.name, COUNT(*) as count " +
                "FROM selection s " +
                "JOIN formation f ON s.formation_id = f.id " +
                "GROUP BY f.id, f.name";
        // Try making a statement and executing the query.
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            // Loop through the result set with a while loop.
            while (rs.next()) {
                // for each row in the result set, add the name and count to the list of results.
                results.add(rs.getString("name") + ": " + rs.getInt("count"));
            }
            // Catch any exceptions and throw a runtime exception.
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Return the list of strings.
        return results;
    }
}
