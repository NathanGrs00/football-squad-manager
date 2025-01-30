package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.PlayerDAO;

public class PlayerService {
    private PlayerDAO playerDAO;

    public PlayerService() {
        playerDAO = new PlayerDAO();
    }

    public void insertPlayer(String firstName, String lastName, int age, String prefFoot, int shirtNumber, String status) {
        int id = 0;
        String firstLetterFoot = prefFoot.substring(0, 1);

        Player player = new Player(id, firstName, lastName, age, firstLetterFoot, shirtNumber, status);
        playerDAO.insertPlayer(player);
    }
}
