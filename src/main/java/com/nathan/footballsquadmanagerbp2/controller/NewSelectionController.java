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

// Controller for validating the fields in the NewSelectionView and creating a new Selection.
public class NewSelectionController {
    // Services and DAO classes that are needed in this class.
    private final SelectionService selectionService;
    private final FormationDAO formationDAO;
    private final AlertService alertService;

    // Constructor that initializes the services and DAO classes.
    public NewSelectionController() {
        // Initialize the services and DAO classes.
        formationDAO = new FormationDAO();
        selectionService = new SelectionService();
        alertService = new AlertService();
    }

    // This gets the list of all formations from the database.
    public List<Formation> getFormations() {
        return formationDAO.getAllFormations();
    }

    // Validation method.
    public Selection validateFields(TextField name, ComboBox<Formation> formation) {
        // Getting the text from the name field.
        String txtname = name.getText();
        // Getting the formation from the combo box.
        Formation selectedFormation = formation.getValue();
        // Getting the current date.
        LocalDate currentDate = LocalDate.now();
        // Converting the current date to SQL date.
        Date sqlCurrentDate = Date.valueOf(currentDate);

        // Checking if the name or formation is empty.
        if (txtname.isEmpty() || selectedFormation == null) {
            // If the name or formation is empty, show an alert.
            alertService.getAlert("Please fill in all required fields.");
            // Return null to indicate that the validation failed.
            return null;
            // Or, if the name is too long, show a different alert.
        } else if (txtname.length() > 25) {
            alertService.getAlert("The selection name cannot exceed 25 characters.");
            // Return null to indicate that the validation failed.
            return null;
        }

        // Check if there is a user that is logged in.
        User loggedInUser = LoginService.getInstance().getLoggedInUser();
        // Make a selection variable, that is null at first.
        Selection selection = null;
        // Check if the login check returned something.
        if (loggedInUser != null) {
            // Create a new selection with the name, date, user and formation.
            selection = selectionService.saveSelection(
                    new Selection(
                            0,
                            txtname,
                            sqlCurrentDate,
                            loggedInUser,
                            selectedFormation
                    )
            );
        }
        // return the selection, or null.
        return selection;
    }

    // This method gets the formation counts by name from the database.
    public List<String> getFormationCountsByName() {
        // return the list of formation counts by name, including the formation name and the count.
        return formationDAO.getFormationCountsByName();
    }
}
