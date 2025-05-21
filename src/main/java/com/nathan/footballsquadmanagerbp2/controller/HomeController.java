package com.nathan.footballsquadmanagerbp2.controller;


import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetail;

import java.util.ArrayList;
import java.util.List;

// Controller class to send to new areas.
public class HomeController {
    FootballSquadManager footballSquadManager;

    // HomeController constructor, instantiating the FootballSquadManager.
    public HomeController(){
        footballSquadManager = new FootballSquadManager();
    }

    // Sends to the overview.
    public void sendToPlayers(){
        footballSquadManager.getAllPlayers();
    }

    // Sends to the new selection.
    public void sendToNewSelection(){
        footballSquadManager.getNewSelection();
    }

    // Passing an empty list, because of the add function, not edit.
    public void sendToBuilder(Selection selection) {
        List<SelectionDetail> emptyList = new ArrayList<>();
        // Sends to the builder.
        footballSquadManager.getBuilder(selection, emptyList);
    }

    // Sends to the selection overview.
    public void sendToAllSelections(){
        footballSquadManager.getAllSelections();
    }
}
