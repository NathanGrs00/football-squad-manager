package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.service.LoginService;

public class LoginController {
    private final LoginService loginService = new LoginService();

    // Checking if inputs are empty, and if they are the correct password TODO: this needs to change.
    public String checkLogin(String username, String password){
        boolean isValid = loginService.checkLogin(username, password);

        if(username.isEmpty() || password.isEmpty()){
            return "Username or password is empty";
        } else if (isValid){
            // Sending to the homescreen.
            FootballSquadManager footballSquadManager = new FootballSquadManager();
            footballSquadManager.getHomescreen();
        }
        return "Login Unsuccessful";
    }
}
