package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.SelectionDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SelectionService {
    SelectionDAO selectionDAO;

    public SelectionService() {
        selectionDAO = new SelectionDAO();
    }

    // Inserting selection and returning the selection so It can be used by the SelectionBuilderView.
    public Selection saveSelection(Selection selection) {
        return selectionDAO.insertNewSelection(selection);
    }

    public void deleteSelection(Selection selection) {
        selectionDAO.deleteSelection(selection);
    }

    // Getting an ArrayList of all Selections.
    public ArrayList<Selection> getAllSelections() {
        ArrayList<Selection> selections = new ArrayList<>();
        ResultSet allSelections = selectionDAO.getAllSelections();

        try {
            while (allSelections.next()) {
                selections.add(new Selection(allSelections));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return selections;
    }
}
