package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.model.User;
import com.nathan.footballsquadmanagerbp2.service.LoginService;

public class LoginController {

    // Checking if inputs are empty, and if they are the correct password.
    public String checkLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return "Username or password cannot be empty.";
        }

        // Checking if login corresponds to a User.
        User loggedInUser = LoginService.getInstance().checkLogin(username, password);

        // If there is a user, send user to the homescreen.
        if (loggedInUser != null) {
            FootballSquadManager footballSquadManager = new FootballSquadManager();
            footballSquadManager.getHomescreen();
            return "Login Successful";
        }

        return "Invalid username or password.";
    }
}
