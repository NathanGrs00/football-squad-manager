package com.nathan.footballsquadmanagerbp2.controller;


import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetail;

import java.util.ArrayList;
import java.util.List;

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

    // Passing an empty list, because of the add function, not edit.
    public void sendToBuilder(Selection selection) {
        List<SelectionDetail> emptyList = new ArrayList<>();
        footballSquadManager.getBuilder(selection, emptyList);
    }

    public void sendToAllSelections(){
        footballSquadManager.getAllSelections();
    }
}
