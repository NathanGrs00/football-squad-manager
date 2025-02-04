package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.view.AllPlayersView;
import com.nathan.footballsquadmanagerbp2.view.PlayerDetailsView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AllPlayersController {
    private final PlayerService playerService;

    public AllPlayersController() {
        this.playerService = new PlayerService();
    }

    // Navigating to the new stage, without player.
    public void addPlayer(AllPlayersView allPlayersView) {
        new PlayerDetailsView(null, allPlayersView);
    }

    // Navigating to the new stage, with player.
    public void editPlayer(Player player, AllPlayersView allPlayersView) {
        new PlayerDetailsView(player, allPlayersView);
    }

    // Confirming the user wants to delete the player.
    public void deletePlayer(Player player) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Player");
        alert.setHeaderText("Are you sure you want to delete this player?");
        alert.setContentText("This action cannot be undone.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            playerService.deletePlayer(player);
        }
    }

    // Calling playerService to get all the players in the database.
    public ObservableList<Player> getAllPlayers() {
        return playerService.getPlayers();
    }
}
