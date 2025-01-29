package com.nathan.footballsquadmanagerbp2.controller;


import com.nathan.footballsquadmanagerbp2.FootballSquadManager;

public class HomeController {
    FootballSquadManager footballSquadManager;

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
