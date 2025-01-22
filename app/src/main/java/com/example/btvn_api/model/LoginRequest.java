package com.example.btvn_api.model;

public class LoginRequest {
    private String email;
    private String password;

    // Constructor, getters, and setters
    public LoginRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
