package com.nathan.footballsquadmanagerbp2.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

// Selection class representing a selection made by a user for a specific formation.
public class Selection {
    // Storing User and Formation in Selection.
    private final int selectionId;
    private final String selectionName;
    private final Date selectionDate;
    private final User selectionUser;
    private final Formation selectionFormation;

    // Constructor to create a Selection object from a ResultSet.
    // This constructor is used when retrieving a selection from the database.
    public Selection(ResultSet rs) throws SQLException {
        this.selectionId = rs.getInt("id");
        this.selectionName = rs.getString("name");
        this.selectionDate = rs.getDate("date");
        // Fetching the user and formation associated with this selection.
        UserDAO userDAO = new UserDAO();
        this.selectionUser = userDAO.getUserById(rs.getInt("user_id"));
        // Fetching the formation associated with this selection.
        FormationDAO formationDAO = new FormationDAO();
        this.selectionFormation = formationDAO.getFormationById(rs.getInt("formation_id"));
    }

    // Constructor to create a Selection object with all attributes.
    public Selection(int selectionId, String selectionName, Date selectionDate, User selectionUser, Formation selectionFormation) {
        this.selectionId = selectionId;
        this.selectionName = selectionName;
        this.selectionDate = selectionDate;
        this.selectionUser = selectionUser;
        this.selectionFormation = selectionFormation;
    }

    // Getters for the attributes of the Selection class.
    public int getSelectionId() {
        return selectionId;
    }

    public String getSelectionName() {
        return selectionName;
    }

    public Date getSelectionDate() {
        return selectionDate;
    }

    public User getSelectionUser() {
        return selectionUser;
    }

    public Formation getSelectionFormation() {
        return selectionFormation;
    }
}
