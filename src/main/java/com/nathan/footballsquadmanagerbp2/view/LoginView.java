package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.LoginController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LoginView {
    private Scene loginScene;
    private LoginController controller;

    private Pane rootPane;
    private VBox loginFields;

    private Image logoPath;
    private ImageView clubLogo;

    private VBox usernameBox;
    private Label usernameTag;
    private TextField txtUsername;
    private VBox passwordBox;
    private Label passwordTag;
    private TextField txtPassword;

    private Button btnLogin;
    private Label returnLabel;

    private double loginWidth;
    private double loginHeight;

    public LoginView() {
        controller = new LoginController();

        loginWidth = FootballSquadManager.screenSize[0] - 300;
        loginHeight = FootballSquadManager.screenSize[1] - 200;

        logoPath = new Image(getClass().getResource("/images/logo_fc_club_main.png").toExternalForm());
        clubLogo = new ImageView(logoPath);


        usernameTag = new Label("Username");
        txtUsername = new TextField();
        usernameBox = new VBox(usernameTag, txtUsername);
        passwordTag = new Label("Password");
        txtPassword = new TextField();
        passwordBox = new VBox(passwordTag, txtPassword);

        btnLogin = new Button("Login");
        returnLabel = new Label();

        btnLogin.setOnAction(e -> {
            handleLoginButton();
        });

        initLayout();
        applyStyling();
    }

    public Scene getScene() {
        loginScene = new Scene(rootPane, loginWidth, loginHeight);
        loginScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        loginScene.getStylesheets().add(getClass().getResource("/stylesheets/login-stylesheet.css").toExternalForm());
        return loginScene;
    }

    private void initLayout() {
        rootPane = new Pane();
        loginFields = new VBox();
    }

    private void applyStyling(){
        clubLogo.setFitWidth(100);
        clubLogo.setPreserveRatio(true);
        clubLogo.setSmooth(true);
        clubLogo.setId("club-logo");

        usernameBox.setSpacing(5);
        passwordBox.setSpacing(5);
        txtUsername.setMinHeight(40);
        txtUsername.setPromptText("Username");
        txtPassword.setMinHeight(40);
        txtPassword.setPromptText("Password");

        loginFields.setPrefWidth(300);
        loginFields.setPrefHeight(400);
        loginFields.setAlignment(Pos.CENTER);
        loginFields.setSpacing(20);

        double loginBoxGapHeight = loginHeight - loginFields.getPrefHeight();
        double loginBoxGapWidth = loginWidth - loginFields.getPrefWidth();

        loginFields.setLayoutY(loginBoxGapHeight / 2);
        loginFields.setLayoutX(loginBoxGapWidth / 2);
        loginFields.getChildren().addAll(clubLogo, usernameBox, passwordBox, btnLogin, returnLabel);

        rootPane.setId("root-pane");
        rootPane.getChildren().add(loginFields);
    }

    private void handleLoginButton() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        returnLabel.setText(controller.checkLogin(username, password));
    }
}
