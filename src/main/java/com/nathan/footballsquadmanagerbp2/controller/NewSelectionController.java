package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.*;
import com.nathan.footballsquadmanagerbp2.service.LoginService;
import com.nathan.footballsquadmanagerbp2.service.SelectionService;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public Selection validateFields(TextField name, ComboBox<Formation> formation) {
        String txtname = name.getText();
        Formation selectedFormation = formation.getValue();
        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);

        //TODO: fix alerts.

        if (txtname.isEmpty() || selectedFormation == null) {
            return null;
        } else if (txtname.length() > 25) {
            return null;
        }

        User loggedInUser = LoginService.getInstance().getLoggedInUser();
        Selection selection = null;
        if (loggedInUser != null) {
            selection = selectionService.saveSelection(new Selection(0, txtname, sqlCurrentDate, loggedInUser, selectedFormation));
        }
        return selection;
    }

    public List<Player> getBestPlayers(List<Player> availablePlayers, Position position) {
        List<Player> bestPlayers = new ArrayList<>();
        int maxProficiency = -1;

        for (Player player : availablePlayers) {
            int proficiency = selectionService.getPlayerProficiencyForPosition(player.getPlayerId(), position.getPositionId());
            if (proficiency > maxProficiency) {
                maxProficiency = proficiency;
                bestPlayers.clear();
                bestPlayers.add(player);
            } else if (proficiency == maxProficiency) {
                bestPlayers.add(player);
            }
        }
        return bestPlayers;
    }
}
