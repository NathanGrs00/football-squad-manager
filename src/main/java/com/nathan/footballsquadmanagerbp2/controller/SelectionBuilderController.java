package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectionBuilderController {
    private final PositionDAO positionDAO;

    public SelectionBuilderController() {
        positionDAO = new PositionDAO();
    }

    // For all players, get the proficiency.
    public Map<Player, Integer> getPlayersForPosition(int positionId, List<Player> allPlayers) {
        Map<Player, Integer> suitablePlayers = new HashMap<>();

        for (Player player : allPlayers) {
            Integer proficiency = positionDAO.getPlayerProficiency(player.getPlayerId(), positionId);
            if (proficiency == null) {
                proficiency = 0;
            }
            suitablePlayers.put(player, proficiency);
        }

        return suitablePlayers;
    }
}
