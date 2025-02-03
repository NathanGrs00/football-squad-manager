package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.SelectionController;
import com.nathan.footballsquadmanagerbp2.model.Formation;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;

public class NewSelectionView {
    SelectionController selectionController;
    HBox root;
    Pane menuBar;
    VBox formationContent;

    Label selectionNameTag;
    TextField selectionNameInput;
    Label formationTag;
    ComboBox<String> formationChoice;
    List<Formation> formations;

    public NewSelectionView() {
        initVariables();
        initLayout();
    }

    public void initVariables() {
        selectionController = new SelectionController();
        root = new HBox();
        formationContent = new VBox();
        menuBar = new MenuBar().createMenuBar();

        selectionNameTag = new Label("Selection name: ");
        selectionNameInput = new TextField();
        formationTag = new Label("Formation: ");
        formationChoice = new ComboBox<>();
        formations = selectionController.getFormations();
    }

    public void initLayout() {
        root.setId("root-pane");
        menuBar.setId("menubar");

        for (Formation formation : formations) {
            formationChoice.getItems().add(formation.getFormationName());
        }
        formationContent.getChildren().addAll(selectionNameTag, selectionNameInput, formationTag,  formationChoice);
        root.getChildren().addAll(menuBar, formationContent);
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/new-selection-stylesheet.css").toExternalForm());
        return homeScene;
    }
}
