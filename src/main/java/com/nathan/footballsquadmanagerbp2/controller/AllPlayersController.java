package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.view.PlayerDetailsView;
import javafx.collections.ObservableList;

public class AllPlayersController {
    PlayerDetailsView showPlayerView;
    PlayerService playerService;

    public void addPlayer() {
        showPlayerView = new PlayerDetailsView(null);
    }

    public void editPlayer(Player player) {
        showPlayerView = new PlayerDetailsView(player);
    }

    public ObservableList<Player> getAllPlayers() {
        playerService = new PlayerService();
        return playerService.getPlayers();
    }
}
