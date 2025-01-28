package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.service.LoginService;

public class LoginController {
    private final LoginService loginService = new LoginService();

    public String checkLogin(String username, String password){
        boolean isValid = loginService.checkLogin(username, password);
        if (isValid){
            return "Login Successful";
        }
        return "Login Failed";
    }
}
