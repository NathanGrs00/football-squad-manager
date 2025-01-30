package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.view.ShowPlayerView;

public class AllPlayersController {
    ShowPlayerView showPlayerView;

    public void addPlayer() {
        showPlayerView = new ShowPlayerView(null);
    }

    public void editPlayer(Player player) {
        showPlayerView = new ShowPlayerView(player);
    }
}
