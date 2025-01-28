package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.LoginController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LoginView {
    private LoginController controller;

    private Pane rootPane;
    private VBox loginFields;

    private TextField txtUsername;
    private TextField txtPassword;
    private Button btnLogin;
    private Label returnLabel;

    private double loginWidth;
    private double loginHeight;

    public LoginView() {
        controller = new LoginController();

        loginWidth = FootballSquadManager.screenSize[0] - 300;
        loginHeight = FootballSquadManager.screenSize[1] - 200;

        txtUsername = new TextField();
        txtPassword = new TextField();
        btnLogin = new Button("Login");
        returnLabel = new Label();

        btnLogin.setOnAction(e -> {
            handleLoginButton();
        });

        initLayout();
        applyStyling();
    }

    public Scene getScene() {
        return new Scene(rootPane, loginWidth, loginHeight);
    }

    private void initLayout() {
        rootPane = new Pane();
        loginFields = new VBox();
    }

    private void applyStyling(){
        returnLabel.setStyle("-fx-text-fill: white;");

        loginFields.setPrefWidth(300);
        loginFields.setPrefHeight(200);
        loginFields.setAlignment(Pos.CENTER);
        loginFields.setSpacing(20);

        double loginBoxGapHeight = loginHeight - loginFields.getPrefHeight();
        double loginBoxGapWidth = loginWidth - loginFields.getPrefWidth();

        loginFields.setLayoutY(loginBoxGapHeight / 2);
        loginFields.setLayoutX(loginBoxGapWidth / 2);
        loginFields.getChildren().addAll(txtUsername, txtPassword, btnLogin, returnLabel);

        rootPane.setStyle("-fx-background-color: #3e3a62;");
        rootPane.getChildren().add(loginFields);
    }

    private void handleLoginButton() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        returnLabel.setText(controller.checkLogin(username, password));
    }
}
