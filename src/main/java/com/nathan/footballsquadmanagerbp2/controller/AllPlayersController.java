package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.view.AllPlayersView;
import com.nathan.footballsquadmanagerbp2.view.PlayerDetailsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

//Controller for managing all player-related actions in the database.
public class AllPlayersController {
    // Instance of playerService.
    private final PlayerService playerService;

    // Constructor for the AllPlayersController.
    public AllPlayersController() {
        this.playerService = new PlayerService();
    }

    // Navigating to the new stage, without player.
    // This is used for adding a new player.
    public void addPlayer(AllPlayersView allPlayersView) {
        new PlayerDetailsView(null, allPlayersView);
    }

    // Navigating to the new stage, with player.
    // This is used for editing an existing player.
    public void editPlayer(Player player, AllPlayersView allPlayersView) {
        new PlayerDetailsView(player, allPlayersView);
    }

    // Confirming the user wants to delete the player.
    public void deletePlayer(Player player) {
        // Creating an alert to confirm deletion.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Setting the alert title, header, and content.
        alert.setTitle("Delete Player");
        alert.setHeaderText("Are you sure you want to delete this player?");
        alert.setContentText("This action cannot be undone.");

        // showAndWait() will wait for the user to respond to the alert.
        // If the user clicks OK, delete the player.
        // If the user clicks Cancel, do nothing.
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            playerService.deletePlayer(player);
        }
    }

    // Calling playerService to get all the players in the database and making an ObservableList for the table.
    public ObservableList<Player> getAllPlayers() {
        // Making an ArrayList of players.
        ArrayList<Player> players = playerService.getPlayers();
        // Creating an ObservableList of players, so it can be used in the table.
        ObservableList<Player> allPlayers = FXCollections.observableArrayList();
        // Add the players to the ObservableList.
        allPlayers.addAll(players);
        // Returning the ObservableList.
        return allPlayers;
    }
}
