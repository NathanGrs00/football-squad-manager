package com.nathan.footballsquadmanagerbp2.controller;


import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.model.Selection;

public class HomeController {
    FootballSquadManager footballSquadManager;

    // Controller class to send to new areas.

    public HomeController(){
        footballSquadManager = new FootballSquadManager();
    }

    public void sendToPlayers(){
        footballSquadManager.getAllPlayers();
    }

    public void sendToNewSelection(){
        footballSquadManager.getNewSelection();
    }

    public void sendToBuilder(Selection selection) {
        footballSquadManager.getBuilder(selection);
    }

    public void sendToAllSelections(){

    }
}
