package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.SelectionDAO;

public class SelectionService {
    SelectionDAO selectionDAO;

    public void saveFormation(Selection selection) {
        selectionDAO = new SelectionDAO();
        selectionDAO.insertNewSelection(selection);
    }
}
