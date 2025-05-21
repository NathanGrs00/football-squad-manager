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
    // DAO and Service classes
    private final PositionDAO positionDAO;
    private final PlayerService playerService;
    private final SelectionDetailsService selectionDetailsService;

    // Constructor to instantiate the DAO and Service classes
    public SelectionBuilderController() {
        positionDAO = new PositionDAO();
        playerService = new PlayerService();
        selectionDetailsService = new SelectionDetailsService();
    }

    // Turns a list of players into a list of available players.
    public List<Player> getAvailablePlayers() {
        // Get all players from the player service
        List<Player> players = playerService.getPlayers();
        // remove the player if they are not available, injured or suspended.
        players.removeIf(player -> player.getPlayerStatus().equals("Not available"));
        players.removeIf(player -> player.getPlayerStatus().equals("Injured"));
        players.removeIf(player -> player.getPlayerStatus().equals("Suspended"));
        // Give the list back.
        return players;
    }

    // Maps the id of selectionDetails (players that have a spot in a selection) to the right player.
    public Map<Integer, Player> getPlayerPositionMap(List<SelectionDetail> selectionDetails) {
        // Make a map, the integer is the positionId and the player is the player.
        // In this case, it is a HashMap, because it is faster, and we don't need to sort the players.
        Map<Integer, Player> positionPlayerMap = new HashMap<>();
        // Loop through the List of selectionDetails.
        for (SelectionDetail detail : selectionDetails) {
            // Make a player object from the playerId in the selectionDetails.
            Player player = playerService.getPlayerById(detail.getPlayerId());
            // If the player is not null, add it to the map.
            if (player != null) {
                // Put the player in the map with the positionId as key.
                positionPlayerMap.put(detail.getPositionId(), player);
            }
            // This means that we now have a map of all players that are in the selection.
        }
        // Return the map.
        return positionPlayerMap;
    }

    // For all players, link the proficiency with the player.
    public Map<Player, Integer> getPlayersForPosition(int positionId, List<Player> allPlayers) {
        // Make a Hashmap, with the Player as key and the proficiency as value.
        // Hashmaps are faster than lists, and we don't need to sort the players.
        Map<Player, Integer> suitablePlayers = new HashMap<>();

        // Loop through the list of players.
        for (Player player : allPlayers) {
            // Get the proficiency of the player for the positionId.
            Integer proficiency = positionDAO.getPlayerProficiency(player.getPlayerId(), positionId);
            // If the proficiency is null, set it to 0.
            if (proficiency == null) {
                proficiency = 0;
            }
            // Put the player and the proficiency in the map.
            suitablePlayers.put(player, proficiency);
        }
        // return the proficiency map.
        return suitablePlayers;
    }

    //Gets the proficiency map above and adds the players to a Hashmap of available players if they have not yet been selected.
    public Map<Player, Integer> getProficientPlayers(int positionId, List<Player> players, Set<Player> selectedPlayers) {
        // Get the proficiency map, which contains the players and their proficiency.
        Map<Player, Integer> playerProficiencyMap = getPlayersForPosition(positionId, players);
        // Make a new HashMap, with the Player as key and the proficiency as value.
        Map<Player, Integer> availablePlayers = new HashMap<>();

        // Loop through the proficiency map.
        for (Map.Entry<Player, Integer> entry : playerProficiencyMap.entrySet()) {
            // If in the selectedPlayers, there is no player that has an entry in the proficiency map
            if (!selectedPlayers.contains(entry.getKey())) {
                // Add the player to the available players map.
                availablePlayers.put(entry.getKey(), entry.getValue());
            }
        }
        // Return the available players map.
        return availablePlayers;
    }

    //Returns true if player is selected, false if player is already in the selection.
    public boolean updateSelection(int selectionId, Player previousPlayer, Player selectedPlayer, int positionId, Set<Player> selectedPlayers) {
        // Prevents selectedPlayer to be selected again.
        // Checks if the selectedPlayer is already in the selection.
        if (selectedPlayers.contains(selectedPlayer)) {
            // Make sure the rest of the code is not executed.
            return false;
        }

        // Removing the previous player to be able to select him again.
        if (previousPlayer != null) {
            // Remove the previous player from the selection.
            selectionDetailsService.removePlayerFromSelection(selectionId, previousPlayer.getPlayerId(), positionId);
            // Also remove the previous player from the list of selectedPlayers.
            selectedPlayers.remove(previousPlayer);
        }

        // Insert player into database because he can be selected, also add him to the list of selectedPlayers.
        selectedPlayers.add(selectedPlayer);
        selectionDetailsService.insertDetails(selectionId, selectedPlayer.getPlayerId(), positionId);

        // Return true, because the player is successfully selected.
        return true;
    }
}
