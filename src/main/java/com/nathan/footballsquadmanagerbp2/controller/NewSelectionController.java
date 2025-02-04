package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Formation;
import com.nathan.footballsquadmanagerbp2.model.FormationDAO;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.service.SelectionService;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class NewSelectionController {
    private SelectionService selectionService;
    private FormationDAO formationDAO;

    public NewSelectionController() {
        formationDAO = new FormationDAO();
        selectionService = new SelectionService();
    }

    public List<Formation> getFormations() {
        return formationDAO.getAllFormations();
    }

    public boolean validateFields(TextField name, ComboBox<Formation> formation) {
        String txtname = name.getText();
        Formation selectedFormation = formation.getValue();
        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);

        selectionService.saveFormation(new Selection(0, txtname, sqlCurrentDate, userId, selectedFormation));
    }
}
