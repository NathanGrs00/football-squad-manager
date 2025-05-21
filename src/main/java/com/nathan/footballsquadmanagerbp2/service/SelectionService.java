package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.SelectionDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// This class is responsible for the logic of the Selection.
public class SelectionService {
    // The SelectionDAO is used to access the database.
    SelectionDAO selectionDAO;

    // Constructor for the SelectionService.
    public SelectionService() {
        selectionDAO = new SelectionDAO();
    }

    // Inserting selection and returning the selection so it can be used by the SelectionBuilderView.
    public Selection saveSelection(Selection selection) {
        return selectionDAO.insertNewSelection(selection);
    }

    // Deleting a selection.
    public void deleteSelection(Selection selection) {
        selectionDAO.deleteSelection(selection);
    }

    // Getting an ArrayList of all Selections.
    public ArrayList<Selection> getAllSelections() {
        // Creating an ArrayList of Selections.
        ArrayList<Selection> selections = new ArrayList<>();
        // Getting all selections from the database.
        ResultSet allSelections = selectionDAO.getAllSelections();

        try {
            // Looping through all selections and adding them to the ArrayList.
            while (allSelections.next()) {
                // Creating a new Selection object and adding it to the ArrayList.
                selections.add(new Selection(allSelections));
            }
        } catch(SQLException e) {
            // throwing a runtime exception if there is an error.
            throw new RuntimeException(e);
        }
        // Returning the ArrayList of Selections.
        return selections;
    }
}
