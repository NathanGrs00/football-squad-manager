package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShowPlayerView {
    private Stage popupStage;
    private Player player;
    private VBox root;

    private HBox nameBox;
    private TextField firstNameField;
    private TextField lastNameField;

    private VBox fieldBox;
    private TextField prefFootField;
    private ComboBox favPositionField;
    private ObservableList<String> favPositions;
    private TextArea otherPositionsField;
    private TextField ageField;
    private TextField shirtNumberField;
    private ComboBox statusField;
    private ObservableList<String> statuses;

    private HBox buttonBox;
    private Button goBackButton;
    private Button saveButton;

    public ShowPlayerView(Player player) {
        this.player = player;
        initLayouts();
        applyStyling();
        handleButtonClicks();
    }

    private void initLayouts(){
        popupStage = new Stage();
        root = new VBox();
        nameBox = new HBox();
        firstNameField = new TextField();
        lastNameField = new TextField();

        fieldBox = new VBox();
        prefFootField = new TextField();

        favPositionField = new ComboBox<>();
        favPositions = FXCollections.observableArrayList(
                //TODO: get abbreviations from the positions table
        );

        favPositionField.setItems(favPositions);

        otherPositionsField = new TextArea();
        ageField = new TextField();
        shirtNumberField = new TextField();

        statusField = new ComboBox<>();
        statuses = FXCollections.observableArrayList(
                "Available", "Not available", "Injured", "Suspended"
        );
        statusField.setItems(statuses);

        buttonBox = new HBox();
        goBackButton = new Button("Go Back");
        saveButton = new Button("Save");

        if (player != null) {
            firstNameField.setText(player.getPlayerFirstName());
            lastNameField.setText(player.getPlayerLastName());
            prefFootField.setText(player.getPlayerPrefFoot());
            ageField.setText(String.valueOf(player.getPlayerAge()));
            favPositionField.setValue("test"); //TODO make combobox work from position_player dao.
            otherPositionsField.setText("test"); //TODO same here.
            shirtNumberField.setText(String.valueOf(player.getPlayerShirtNumber()));
            statusField.setValue(player.getPlayerStatus());
        }
    }

    private void applyStyling(){
        root.setId("root-pane");
        nameBox.setId("name-box");
        otherPositionsField.setId("other-positions-field");
        fieldBox.setId("field-box");
        buttonBox.setId("button-box");

        Image clubIcon = new Image(getClass().getResource("/images/logo_fc_club_second.png").toExternalForm(), 500, 500, true, true);
        popupStage.getIcons().add(clubIcon);
        if (player == null) {
            popupStage.setTitle("Add new player");
        } else {
            popupStage.setTitle("Edit player: " + player.getPlayerFirstName() + " " + player.getPlayerLastName());
        }
        popupStage.setResizable(false);
        popupStage.setScene(getScene());
        popupStage.show();

        nameBox.getChildren().addAll(firstNameField, lastNameField);
        fieldBox.getChildren().addAll(prefFootField, ageField, favPositionField, otherPositionsField, shirtNumberField, statusField);
        buttonBox.getChildren().addAll(goBackButton, saveButton);

        root.getChildren().addAll(nameBox, fieldBox, buttonBox);
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, 600, 600);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/playerdetails-stylesheet.css").toExternalForm());
        return homeScene;
    }

    private void handleButtonClicks(){
        goBackButton.setOnAction(event -> {popupStage.close();});
        saveButton.setOnAction(event -> {
            //TODO add player to database || edit existing player (ifcheck).
            });
    }
}
