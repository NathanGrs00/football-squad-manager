package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.HomeController;
import com.nathan.footballsquadmanagerbp2.controller.NewSelectionController;
import com.nathan.footballsquadmanagerbp2.model.Formation;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

// This class is responsible for the view of the new selection screen.
public class NewSelectionView {
    // Controller for the new selection screen.
    private NewSelectionController selectionController;

    // Layout variables.
    private HBox root;
    private Pane menuBar;
    private VBox formationContent;
    private VBox formationAmountBox;

    // Labels and input fields.
    private Label selectionNameTag;
    private TextField selectionNameInput;
    private Label formationTag;
    // ComboBox for formations.
    private ComboBox<Formation> formationChoice;
    private List<Formation> formations;

    // Button to submit the selection.
    private Button submitButton;

    // Constructor for the NewSelectionView class.
    public NewSelectionView() {
        initVariables();
        initLayout();
    }

    // Initializing variables.
    public void initVariables() {
        // Initializing the controller and layout variables.
        selectionController = new NewSelectionController();
        root = new HBox();
        formationContent = new VBox();
        formationAmountBox = new VBox();
        // Creating the menu bar, from the MenuBar class.
        menuBar = new MenuBar().createMenuBar();

        // Creating the labels and input fields.
        selectionNameTag = new Label("Selection name: ");
        selectionNameInput = new TextField();
        formationTag = new Label("Formation: ");
        formationChoice = new ComboBox<>();
        // Getting the formations from the controller.
        formations = selectionController.getFormations();

        // Adding a logo to the button.
        Image saveLogo = new Image(getClass().getResource("/icons/save_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView saveLogoView = new ImageView(saveLogo);
        submitButton = new Button("Make selection");
        submitButton.setGraphic(saveLogoView);
        // Displaying the amount of formations saved, so I can add a GROUP BY clause to the database.
        List<String> formationCounts = selectionController.getFormationCountsByName();
        // Add a label to the formation amount box, for context.
        formationAmountBox.getChildren().add(new Label("Current selections saved:"));
        // Each formation count is a label.
        for (String count : formationCounts) {
            // Add a label to the formation amount box.
            formationAmountBox.getChildren().add(new Label(count));
        }
    }

    // Layout choices.
    public void initLayout() {
        root.setId("root-pane");
        menuBar.setId("menubar");
        formationContent.setId("formation-content");
        // Setting the preferred size of the formation content.
        formationContent.setPrefWidth(FootballSquadManager.screenSize[0] - 300);
        // if the submit button is pressed, call the handleButtonClick method.
        submitButton.setOnAction(_ -> handleButtonClick());

        // For each formation, add it to the combo box.
        for (Formation formation : formations) {
            formationChoice.getItems().add(formation);
        }
        // Add all the components to the formation content.
        formationContent.getChildren().addAll(formationAmountBox, selectionNameTag, selectionNameInput, formationTag, formationChoice, submitButton);
        // Together with the menu bar, in the root.
        root.getChildren().addAll(menuBar, formationContent);
    }

    // Getting the scene.
    public Scene getScene() {
        // Creating the scene with the root pane and setting the size.
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        // Getting the font and stylesheet.
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/new-selection-stylesheet.css").toExternalForm());
        // returning the scene.
        return homeScene;
    }

    // Sending to the controller checks.
    public void handleButtonClick() {
        // Validating the fields and getting the selection.
        Selection selection = selectionController.validateFields(selectionNameInput, formationChoice);
        // Sending the selection to the controller.
        HomeController homeController = new HomeController();
        homeController.sendToBuilder(selection);
    }
}
