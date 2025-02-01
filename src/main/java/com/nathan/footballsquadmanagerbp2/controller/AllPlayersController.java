package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.view.AllPlayersView;
import com.nathan.footballsquadmanagerbp2.view.PlayerDetailsView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AllPlayersController {
    private PlayerDetailsView showPlayerView;
    private PlayerService playerService;

    public void addPlayer(AllPlayersView allPlayersView) {
        showPlayerView = new PlayerDetailsView(null, allPlayersView);
    }

    public void editPlayer(Player player, AllPlayersView allPlayersView) {
        showPlayerView = new PlayerDetailsView(player, allPlayersView);
    }

    public void deletePlayer(Player player) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this player?",
                ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Delete Player");
        alert.setHeaderText(null);

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            playerService.deletePlayer(player);
        }
    }

    public ObservableList<Player> getAllPlayers() {
        playerService = new PlayerService();
        return playerService.getPlayers();
    }
}
