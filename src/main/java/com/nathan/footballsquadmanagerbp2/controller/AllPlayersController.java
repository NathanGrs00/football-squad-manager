package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.view.PlayerDetailsView;

public class AllPlayersController {
    PlayerDetailsView showPlayerView;

    public void addPlayer() {
        showPlayerView = new PlayerDetailsView(null);
    }

    public void editPlayer(Player player) {
        showPlayerView = new PlayerDetailsView(player);
    }
}
