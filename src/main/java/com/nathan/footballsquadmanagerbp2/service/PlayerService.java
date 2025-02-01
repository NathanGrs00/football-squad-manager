package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.PlayerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerService {
    private final PlayerDAO playerDAO;

    public PlayerService() {
        playerDAO = new PlayerDAO();
    }

    public void insertPlayer(String firstName, String lastName, int age, String prefFoot, int shirtNumber, String status, String favPos) {
        int id = 0;
        String firstLetterFoot = prefFoot.substring(0, 1);

        Player player = new Player(id, firstName, lastName, age, firstLetterFoot, shirtNumber, status);


        int generatedId = playerDAO.insertPlayer(player);
        PositionService positionService = new PositionService();
        positionService.setPlayerBestPosition(generatedId, favPos);
    }

    public void deletePlayer(Player player) {
        int id = player.getPlayerId();
        playerDAO.deletePlayer(id);
    }

    public ObservableList<Player> getPlayers() {
        ObservableList<Player> players = FXCollections.observableArrayList();
        ResultSet allPlayers = playerDAO.getAllPlayers();

        try {
            while (allPlayers.next()) {
                players.add(new Player(allPlayers));
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return players;
    }
}
