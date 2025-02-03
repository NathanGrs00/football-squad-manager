package com.nathan.footballsquadmanagerbp2.service;

public class LoginService {
    // TODO: Make a loginDAO and hash the passwords here.
    public boolean checkLogin(String username, String password) {
        return username.equals("manager") && password.equals("admin");
    }
}
