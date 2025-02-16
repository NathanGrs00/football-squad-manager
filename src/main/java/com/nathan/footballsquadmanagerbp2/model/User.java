package com.nathan.footballsquadmanagerbp2.model;

public class User {
    private final int userId;
    private final String userName;
    private final String password;
    private final String role;

    public User(int userId, String userName, String password, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    // Role for future role-based permissions
    public String getRole() {
        return role;
    }

    // Ensures userName can be used in a tableview property.
    @Override
    public String toString() {
        return userName;
    }
}
