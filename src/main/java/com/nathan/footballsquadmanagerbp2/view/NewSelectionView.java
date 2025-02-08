package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.HomeController;
import com.nathan.footballsquadmanagerbp2.controller.NewSelectionController;
import com.nathan.footballsquadmanagerbp2.model.Formation;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class NewSelectionView {

    private NewSelectionController selectionController;
    private HBox root;
    private Pane menuBar;
    private VBox formationContent;

    private Label selectionNameTag;
    private TextField selectionNameInput;
    private Label formationTag;
    private ComboBox<Formation> formationChoice;
    private List<Formation> formations;

    private Button submitButton;

    public NewSelectionView() {
        initVariables();
        initLayout();
    }

    public void initVariables() {
        selectionController = new NewSelectionController();
        root = new HBox();
        formationContent = new VBox();
        menuBar = new MenuBar().createMenuBar();

        selectionNameTag = new Label("Selection name: ");
        selectionNameInput = new TextField();
        formationTag = new Label("Formation: ");
        formationChoice = new ComboBox<>();
        formations = selectionController.getFormations();

        submitButton = new Button("Save new selection");
    }

    public void initLayout() {
        root.setId("root-pane");
        menuBar.setId("menubar");
        formationContent.setId("formation-content");
        formationContent.setPrefWidth(FootballSquadManager.screenSize[0] - 300);
        submitButton.setOnAction(_ -> handleButtonClick());

        for (Formation formation : formations) {
            formationChoice.getItems().add(formation);
        }
        formationContent.getChildren().addAll(selectionNameTag, selectionNameInput, formationTag, formationChoice, submitButton);
        root.getChildren().addAll(menuBar, formationContent);
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/new-selection-stylesheet.css").toExternalForm());
        return homeScene;
    }

    public void handleButtonClick() {
        Selection selection = selectionController.validateFields(selectionNameInput, formationChoice);
        HomeController homeController = new HomeController();
        homeController.sendToBuilder(selection);
    }
}
