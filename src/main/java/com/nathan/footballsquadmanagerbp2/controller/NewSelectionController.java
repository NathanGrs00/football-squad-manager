package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.*;
import com.nathan.footballsquadmanagerbp2.service.AlertService;
import com.nathan.footballsquadmanagerbp2.service.LoginService;
import com.nathan.footballsquadmanagerbp2.service.SelectionService;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class NewSelectionController {
    private final SelectionService selectionService;
    private final FormationDAO formationDAO;
    private final AlertService alertService;

    public NewSelectionController() {
        formationDAO = new FormationDAO();
        selectionService = new SelectionService();
        alertService = new AlertService();
    }

    public List<Formation> getFormations() {
        return formationDAO.getAllFormations();
    }

    // Validation method.
    public Selection validateFields(TextField name, ComboBox<Formation> formation) {
        String txtname = name.getText();
        Formation selectedFormation = formation.getValue();
        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);

        if (txtname.isEmpty() || selectedFormation == null) {
            alertService.getAlert("Please fill in all required fields.");
            return null;
        } else if (txtname.length() > 25) {
            alertService.getAlert("The selection name cannot exceed 25 characters.");
            return null;
        }

        // Making a new Selection if there is a user logged in.
        User loggedInUser = LoginService.getInstance().getLoggedInUser();
        Selection selection = null;
        if (loggedInUser != null) {
            selection = selectionService.saveSelection(new Selection(0, txtname, sqlCurrentDate, loggedInUser, selectedFormation));
        }
        return selection;
    }
}
