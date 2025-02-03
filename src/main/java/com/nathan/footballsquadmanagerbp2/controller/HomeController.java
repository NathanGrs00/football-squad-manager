package com.nathan.footballsquadmanagerbp2.controller;


import com.nathan.footballsquadmanagerbp2.FootballSquadManager;

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

    }

    public void sendToAllSelections(){

    }
}
