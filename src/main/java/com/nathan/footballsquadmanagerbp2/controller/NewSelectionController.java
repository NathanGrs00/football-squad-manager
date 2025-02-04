package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Formation;
import com.nathan.footballsquadmanagerbp2.model.FormationDAO;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.User;
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

    public NewSelectionController() {
        formationDAO = new FormationDAO();
        selectionService = new SelectionService();
    }

    public List<Formation> getFormations() {
        return formationDAO.getAllFormations();
    }

    public String validateFields(TextField name, ComboBox<Formation> formation) {
        String txtname = name.getText();
        Formation selectedFormation = formation.getValue();
        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);

        String alertString = "";

        if (txtname.isEmpty() || selectedFormation == null) {
            alertString = "Please enter all fields";
        } else if (txtname.length() > 25) {
            alertString = "Name is too long";
        }

        User loggedInUser = LoginService.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            selectionService.saveFormation(new Selection(0, txtname, sqlCurrentDate, loggedInUser, selectedFormation));
        }
        return alertString;
    }
}
