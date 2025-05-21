package com.nathan.footballsquadmanagerbp2.model;

import java.sql.ResultSet;
import java.sql.SQLException;

// Formation class to represent a football formation, like 4-4-2 or 3-5-2
public class Formation {
    private final int formationId;
    private final String formationName;

    // Constructor to create a Formation object from a ResultSet
    // This is used when retrieving formations from the database
    public Formation(ResultSet resultSet) throws SQLException {
        this.formationId = resultSet.getInt("id");
        this.formationName = resultSet.getString("name");
    }

    // Constructor to create a Formation object with specified id and name
    public Formation(int formationId, String formationName) {
        this.formationId = formationId;
        this.formationName = formationName;
    }

    // Getters for formationId and formationName
    public int getFormationId() {
        return formationId;
    }
    public String getFormationName() {
        return formationName;
    }

    // Ensures formationName can be used in a combobox, but still gains access to the .getFormationId() getter.
    @Override
    public String toString() {
        return formationName;
    }
}
