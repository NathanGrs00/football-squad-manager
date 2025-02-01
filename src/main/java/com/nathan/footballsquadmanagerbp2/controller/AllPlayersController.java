package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.service.AlertService;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.view.PlayerDetailsView;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AllPlayersController {
    PlayerDetailsView showPlayerView;
    PlayerService playerService;

    public void addPlayer() {
        showPlayerView = new PlayerDetailsView(null);
    }

    public void editPlayer(Player player) {
        showPlayerView = new PlayerDetailsView(player);
    }

    public void deletePlayer(Player player) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Player");
        alert.setContentText("Are you sure you want to delete this player?");
        alert.setHeaderText(null);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            playerService.deletePlayer(player);
        }
    }

    public ObservableList<Player> getAllPlayers() {
        playerService = new PlayerService();
        return playerService.getPlayers();
    }
}
