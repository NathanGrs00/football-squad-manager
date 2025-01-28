package com.nathan.footballsquadmanagerbp2.service;

public class LoginService {
    public boolean checkLogin(String username, String password) {
        return username.equals("manager") && password.equals("admin");
    }
}
