package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.PositionDAO;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetail;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.service.SelectionDetailsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectionBuilderController {
    private final PositionDAO positionDAO;
    private final PlayerService playerService;
    private final SelectionDetailsService selectionDetailsService;

    public SelectionBuilderController() {
        positionDAO = new PositionDAO();
        playerService = new PlayerService();
        selectionDetailsService = new SelectionDetailsService();
    }

    // Removes players from the list of all players if they are injured, not available or suspended.
    public List<Player> getAvailablePlayers() {
        List<Player> players = playerService.getPlayers();
        players.removeIf(player -> player.getPlayerStatus().equals("Not available"));
        players.removeIf(player -> player.getPlayerStatus().equals("Injured"));
        players.removeIf(player -> player.getPlayerStatus().equals("Suspended"));
        return players;
    }

    // Maps the id of selectionDetails (players that have a spot in a selection) to the right player.
    public Map<Integer, Player> getPlayerPositionMap(List<SelectionDetail> selectionDetails) {
        Map<Integer, Player> positionPlayerMap = new HashMap<>();
        for (SelectionDetail detail : selectionDetails) {
            Player player = playerService.getPlayerById(detail.getPlayerId());
            if (player != null) {
                positionPlayerMap.put(detail.getPositionId(), player);
            }
        }
        return positionPlayerMap;
    }

    // For all players, link the proficiency with the player.
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

    //Gets the proficiency map above and adds the players to a Hashmap of available players if they have not yet been selected.
    public Map<Player, Integer> getProficientPlayers(int positionId, List<Player> players, Set<Player> selectedPlayers) {
        Map<Player, Integer> playerProficiencyMap = getPlayersForPosition(positionId, players);
        Map<Player, Integer> availablePlayers = new HashMap<>();

        for (Map.Entry<Player, Integer> entry : playerProficiencyMap.entrySet()) {
            if (!selectedPlayers.contains(entry.getKey())) {
                availablePlayers.put(entry.getKey(), entry.getValue());
            }
        }
        return availablePlayers;
    }

    //Returns true if player is selected, false if player is already in the selection.
    public boolean updateSelection(int selectionId, Player previousPlayer, Player selectedPlayer, int positionId, Set<Player> selectedPlayers) {
        // Prevents selectedPlayer to be selected again.
        if (selectedPlayers.contains(selectedPlayer)) {
            return false;
        }

        // Removing the previous player to be able to select him again.
        if (previousPlayer != null) {
            selectionDetailsService.removePlayerFromSelection(selectionId, previousPlayer.getPlayerId(), positionId);
            selectedPlayers.remove(previousPlayer);
        }

        // Insert player into database because he can be selected, also add him to the list of selectedPlayers.
        selectedPlayers.add(selectedPlayer);
        selectionDetailsService.insertDetails(selectionId, selectedPlayer.getPlayerId(), positionId);

        return true;
    }
}
